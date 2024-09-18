package guru.qa.niffler.test.web;

import guru.qa.niffler.jupiter.extension.UsersQueueExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static guru.qa.niffler.jupiter.extension.UsersQueueExtension.*;

public class UserTest {

    @ExtendWith(UsersQueueExtension.class)
    @Test
    void testWithEmpty1(@UserType(empty = true)StaticUser user) throws InterruptedException {
        Thread.sleep(1000);
        System.out.println(user);
    }
    @ExtendWith(UsersQueueExtension.class)
    @Test
    void testWithEmpty2(@UserType(empty = true) StaticUser user) throws InterruptedException {
        Thread.sleep(1000);
        System.out.println(user);
    }

    @ExtendWith(UsersQueueExtension.class)
    @Test
    void testWithNotEmpty(@UserType(empty = false) StaticUser user) throws InterruptedException {
        Thread.sleep(1000);
        System.out.println(user);
    }
}
