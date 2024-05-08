package com.university.bibliotheca.domain.model;

public enum Occupation {
        COMMON_USER(0),
        STUDENT(1),
        UNIVERSITY_EMPLOYEE(2);

        private int numVal;

        Occupation(int numVal) {
                this.numVal = numVal;
        }

        public int getNumVal() {
                return numVal;
        }
}
