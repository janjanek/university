package com.university.bibliotheca.domain.model;

public enum Occupation {
        COMMON_USER(0),
        STUDENT(1),
        UNIVERSITY_EMPLOYEE(2);

        private Integer numVal;

        Occupation(int numVal) {
                this.numVal = numVal;
        }

        public Integer getNumVal() {
                return numVal;
        }
}
