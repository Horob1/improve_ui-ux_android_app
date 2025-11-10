package com.acntem.improveuiapp.presentation.screen.ux.form

data class FormErrorState(
    val nameError: Boolean = false,
    val emailError: Boolean = false,
    val passwordError: Boolean = false,
    val confirmPasswordError: Boolean = false,
) {
    fun isValid(): Boolean {
        return !(nameError || emailError || passwordError || confirmPasswordError)
    }
}