package br.edu.utfpr.dv.siacoes.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentTest {

    @Test
    void getIdDepartment() {
        Department d = new Department();
        
        department.setName("Teste");
        String name = department.getName();

        assertEquals("Teste", name);
    }


} 
