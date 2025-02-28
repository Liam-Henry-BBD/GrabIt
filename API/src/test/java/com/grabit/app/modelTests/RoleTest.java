package com.grabit.app.modelTests;

import com.grabit.app.model.Role;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class RoleTest {
    @Test
    public void testRoleConstructorAndGetter() {
        Role role = new Role((byte) 1, "lead");
        assertEquals((byte) 1, role.getRoleID());
        assertEquals("lead", role.getRoleTitle());
    }

    @Test
    public void testRoleSetters() {
        Role role = new Role();
        role.setRoleID((byte) 2);
        role.setRoleTitle("admin");
        assertEquals((byte) 2, role.getRoleID());
        assertEquals("admin", role.getRoleTitle());
    }

}
