package guru.qa.niffler.jupiter.extension;

import io.qameta.allure.Allure;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.support.AnnotationSupport;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

public class UsersQueueExtension implements
        BeforeEachCallback,
        AfterEachCallback,
        ParameterResolver {

    public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(UsersQueueExtension.class);

    public record StaticUser(String username,
                             String password,
                             String friend,
                             String income,
                             String outcome) {
    }

    private static final Queue<StaticUser> EMPTY_USERS = new ConcurrentLinkedQueue<>();
    private static final Queue<StaticUser> WITH_FRIEND_USERS = new ConcurrentLinkedQueue<>();
    private static final Queue<StaticUser> WITH_INCOME_REQUEST_USERS = new ConcurrentLinkedQueue<>();
    private static final Queue<StaticUser> WITH_OUTCOME_REQUEST_USERS = new ConcurrentLinkedQueue<>();

    static {
        EMPTY_USERS.add(new StaticUser("test1", "111", null, null, null));
        WITH_FRIEND_USERS.add(new StaticUser("test2", "222", "test3", null, null));
        WITH_INCOME_REQUEST_USERS.add(new StaticUser("test4", "444", null, "test5", null));
        WITH_OUTCOME_REQUEST_USERS.add(new StaticUser("test5", "555", null, null, "test4"));
    }

    @Target(ElementType.PARAMETER)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface UserType {
        Type value() default Type.EMPTY;

        enum Type {
            EMPTY, WITH_FRIEND, WITH_INCOME_REQUEST, WITH_OUTCOME_REQUEST
        }
    }

    private Queue<StaticUser> getQueue(UserType.Type type) {
        return switch (type) {
            case EMPTY -> EMPTY_USERS;
            case WITH_FRIEND -> WITH_FRIEND_USERS;
            case WITH_INCOME_REQUEST -> WITH_INCOME_REQUEST_USERS;
            case WITH_OUTCOME_REQUEST -> WITH_OUTCOME_REQUEST_USERS;
        };
    }

    @SuppressWarnings("unchecked")
    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        Arrays.stream(context.getRequiredTestMethod().getParameters())
                .filter(p -> AnnotationSupport.isAnnotated(p, UserType.class) && p.getType().isAssignableFrom(StaticUser.class))
                .map(p -> p.getAnnotation(UserType.class))
                .forEach(ut -> {
                    Optional<StaticUser> user = Optional.empty();
                    StopWatch sw = StopWatch.createStarted();
                    while (user.isEmpty() && sw.getTime(TimeUnit.SECONDS) < 30) {
                        user =  Optional.ofNullable(getQueue(ut.value()).poll());
                    }
                    user.ifPresentOrElse(
                            u -> ((Map<UserType, StaticUser>) context.getStore(NAMESPACE)
                                    .getOrComputeIfAbsent(
                                            context.getUniqueId(),
                                            key -> new HashMap<>()
                                    )).put(ut, u),
                            () -> {
                                throw new IllegalStateException("Can't find user after 30 sec");
                            }
                    );
                });
        Allure.getLifecycle().updateTestCase(testCase -> {
            testCase.setStart(new Date().getTime());
        });
    }

    @SuppressWarnings("unchecked")
    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        Map<UserType, StaticUser> map = (Map<UserType, StaticUser>) context.getStore(NAMESPACE).get(
                context.getUniqueId(),
                Map.class);
        if (map != null) {
            for (Map.Entry<UserType, StaticUser> e : map.entrySet()) {
                getQueue(e.getKey().value()).add(e.getValue());
            }
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(StaticUser.class)
                && AnnotationSupport.isAnnotated(parameterContext.getParameter(), UserType.class);
    }

    @Override
    public StaticUser resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return (StaticUser) extensionContext.getStore(NAMESPACE).get(extensionContext.getUniqueId(), Map.class).get(
                parameterContext.getParameter().getAnnotation(UserType.class)
        );


    }
}
