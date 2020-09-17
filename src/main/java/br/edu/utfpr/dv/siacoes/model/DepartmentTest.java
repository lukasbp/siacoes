package br.edu.utfpr.dv.siacoes.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentTest {

    @Test
    void TestDepartment() {
        Department d = new Department();
        
        d.setName("Teste");
        String name = d.getName();

        assertEquals("Teste", name);
    }


} 
