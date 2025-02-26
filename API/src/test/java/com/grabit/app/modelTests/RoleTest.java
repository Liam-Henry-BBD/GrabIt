package com.grabit.app.modelTests;

import org.junit.jupiter.api.Test;

import com.grabit.app.model.Role;

import static org.junit.jupiter.api.Assertions.*;

public class RoleTest {
    @Test
    public void testRoleContructorAndGetter(){
        Role role = new Role((byte) 1, "lead");
        assertEquals((byte) 1, role.getRoleId());
        assertEquals("lead", role.getRoleTitle());
    }
    
    @Test
    public void testRoleSetters(){
        Role role = new Role();
        role.setRoleId((byte) 2);
        role.setRoleTitle("admin");
        assertEquals((byte) 2, role.getRoleId());
        assertEquals("admin", role.getRoleTitle());
    }

}
