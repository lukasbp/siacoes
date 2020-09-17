package br.edu.utfpr.dv.siacoes.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentTest {

    @Test
    void getIdDepartment() {
        Department d = new Department();
        d.setIdDepartment(0);
        assertEquals(0, d.getIdDepartment());
    }


} 
