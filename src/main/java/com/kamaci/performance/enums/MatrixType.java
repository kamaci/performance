package com.kamaci.performance.enums;

/**
 * @author Furkan KAMACI
 */
public enum MatrixType {
    //case 1
    PLAIN,
    //case 2
    PLAIN_SYNCHRONIZED,
    //case 3
    LOCK_SYNCHRONIZED,
    //case 4
    ATOMIC_GET_AND_INCREMENT,
    //case 4
    ATOMIC_GET_COMPARE_AND_SET,
}
