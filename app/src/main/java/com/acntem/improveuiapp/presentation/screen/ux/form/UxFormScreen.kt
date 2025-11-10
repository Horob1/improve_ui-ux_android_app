package com.acntem.improveuiapp.presentation.screen.ux.form

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.acntem.improveuiapp.domain.model.FormInfo
import com.acntem.improveuiapp.presentation.common.SimpleSwitchOptimizationLayout
import com.acntem.improveuiapp.presentation.ui.theme.dimens
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// Validate ngay lật tức (on focus)
// Chuyển xuống dòng dưới liền kề
// Đẩy lên khi nhập
// Thứ tự của cancel
@Composable
fun UxFormScreen(
    viewModel: UXFormViewModel,
    onBack: () -> Unit = {},
) {
    val isLoading by viewModel.isLoading.collectAsState()
    val mode by viewModel.state.collectAsState()
    val state by viewModel.formInfo.collectAsState()
    val formErrorState by viewModel.formErrorState.collectAsState()


    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else
        SimpleSwitchOptimizationLayout(
            title = "Improve Form UX",
            isOptimizeMode = mode,
            useVerticalScroll = true,
            onPopBackStack = {
                onBack()
            },
            sharedContent = {
                Card(
                    modifier = Modifier
                        .padding(MaterialTheme.dimens.medium1)
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .padding(MaterialTheme.dimens.medium1)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Optimize Mode")
                        Switch(
                            checked = mode,
                            onCheckedChange = {
                                viewModel.setMode(
                                    !mode
                                )
                            }
                        )
                    }


                }
            },
            optimizeContent = {
                OptimizeForm(
                    formState = state,
                    formError = formErrorState,
                    onValidateForm = { error ->
                        viewModel.setFormErrorState(
                            error
                        )
                    },
                    onClearFormError = {
                        viewModel.clearFormErrorState()
                    },
                    onInfoChange = { info ->
                        viewModel.setFormState(
                            info
                        )
                    },
                    onFormClear = {
                        viewModel.clearFormState()
                    }
                )
            },
            nonOptimizeContent = {
                NonOptimizeForm()
            }
        )
}

@Preview
@Composable
fun OptimizeForm(
    formState: FormInfo = FormInfo(),
    formError: FormErrorState = FormErrorState(),
    onValidateForm: (FormErrorState) -> Unit = {},
    onClearFormError: () -> Unit = {},
    onInfoChange: (FormInfo) -> Unit = {},
    onFormClear: () -> Unit = {},
) {
    var isSubmitForm by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val localFocusManager = LocalFocusManager.current


    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }

    fun isValidName(name: String): Boolean {
        return name.length >= 3
    }

    fun isValidConfirmPassword(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword && isValidPassword(password)
    }

    suspend fun onSubmit() {
        isSubmitForm = true
        delay(5000)
        onFormClear()
        Toast.makeText(context, "Form submitted successfully", Toast.LENGTH_SHORT).show()
        isSubmitForm = false
    }

    val emailRequester = remember {
        FocusRequester()
    }

    val nameRequester = remember {
        FocusRequester()
    }

    val passwordRequester = remember {
        FocusRequester()
    }

    val confirmPasswordRequester = remember {
        FocusRequester()
    }

    fun validateForm(): Boolean {
        return isValidEmail(formState.email) &&
                isValidName(formState.name) &&
                isValidPassword(formState.password) &&
                isValidConfirmPassword(formState.password, formState.confirmPassword)
    }


    fun handleError() {
        localFocusManager.clearFocus()
        onValidateForm(
            FormErrorState(
                emailError = !isValidEmail(formState.email),
                nameError = !isValidName(formState.name),
                passwordError = !isValidPassword(formState.password),
                confirmPasswordError = !isValidConfirmPassword(
                    formState.password,
                    formState.confirmPassword
                )
            )
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(MaterialTheme.dimens.medium1)
                .width(IntrinsicSize.Max),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.small1)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .focusRequester(emailRequester)
                    .onFocusChanged(
                        onFocusChanged = {
                            if (!it.isFocused)
                                onValidateForm(
                                    formError.copy(
                                        emailError = if (formState.email.isEmpty()) false else !isValidEmail(
                                            formState.email
                                        )
                                    )
                                )
                        }
                    ),
                value = formState.email,
                onValueChange = {
                    onInfoChange(
                        formState.copy(
                            email = it
                        )
                    )

                    onValidateForm(
                        formError.copy(
                            emailError = false
                        )
                    )

                },
                label = {
                    Text("Email")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        nameRequester.requestFocus()
                    }
                ),
                enabled = !isSubmitForm,
                readOnly = isSubmitForm,
                placeholder = {
                    Text("Enter your email")
                },
                leadingIcon = {
                    Icon(
                        Icons.Default.Email,
                        contentDescription = "Email"
                    )
                },
                maxLines = 1,
                singleLine = true,
                shape = MaterialTheme.shapes.large,
                isError = formError.emailError,
                supportingText = {
                    if (formError.emailError)
                        Text("Email is invalid")
                }
            )
            OutlinedTextField(
                modifier = Modifier
                    .focusRequester(nameRequester)
                    .onFocusChanged(
                        onFocusChanged = {
                            if (!it.isFocused) {
                                onValidateForm(
                                    formError.copy(
                                        nameError = if (formState.name.isEmpty()) false else !isValidName(
                                            formState.name
                                        )
                                    )
                                )
                            }
                        }
                    ),
                value = formState.name,
                onValueChange = {
                    onInfoChange(
                        formState.copy(
                            name = it
                        )
                    )

                    onValidateForm(
                        formError.copy(
                            nameError = false
                        )
                    )
                },
                label = {
                    Text("Name")
                },
                enabled = !isSubmitForm,
                readOnly = isSubmitForm,
                placeholder = {
                    Text("Enter your name")
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        passwordRequester.requestFocus()
                    }
                ),
                leadingIcon = {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = "Name"
                    )
                },
                maxLines = 1,
                singleLine = true,
                shape = MaterialTheme.shapes.large,
                isError = formError.nameError,
                supportingText = {
                    if (formError.nameError)
                        Text("Name must be at least 3 characters")
                }
            )
            OutlinedTextField(
                modifier = Modifier
                    .focusRequester(passwordRequester)
                    .onFocusChanged(
                        onFocusChanged = {
                            if (!it.isFocused)
                                onValidateForm(
                                    formError.copy(
                                        passwordError = if (formState.password.isEmpty()) false else !isValidPassword(
                                            formState.password
                                        )
                                    )
                                )
                        }
                    ),
                value = formState.password,
                onValueChange = {
                    onInfoChange(
                        formState.copy(
                            password = it
                        )
                    )
                    onValidateForm(
                        formError.copy(
                            passwordError = false
                        )
                    )
                },
                label = {
                    Text("Password")
                },
                enabled = !isSubmitForm,
                readOnly = isSubmitForm,
                placeholder = {
                    Text("Enter your password")
                },
                leadingIcon = {
                    Icon(
                        Icons.Default.Lock,
                        contentDescription = "Password"
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        confirmPasswordRequester.requestFocus()
                    }
                ),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (!passwordVisible)
                        Icons.Default.Visibility
                    else
                        Icons.Default.VisibilityOff

                    val description = if (passwordVisible) "Ẩn mật khẩu" else "Hiện mật khẩu"

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, contentDescription = description)
                    }
                },
                maxLines = 1,
                singleLine = true,
                shape = MaterialTheme.shapes.large,
                isError = formError.passwordError,
                supportingText = {
                    if (formError.passwordError)
                        Text("Password must be at least 6 characters")
                }
            )
            OutlinedTextField(
                modifier = Modifier
                    .focusRequester(confirmPasswordRequester)
                    .onFocusChanged(
                        onFocusChanged = {
                            if (!it.isFocused)
                                onValidateForm(
                                    formError.copy(
                                        confirmPasswordError = if (formState.confirmPassword.isEmpty()) false else !isValidConfirmPassword(
                                            formState.password,
                                            formState.confirmPassword
                                        )
                                    )
                                )
                        }
                    ),
                value = formState.confirmPassword,
                onValueChange = {
                    onInfoChange(
                        formState.copy(
                            confirmPassword = it
                        )
                    )
                    onValidateForm(
                        formError.copy(
                            confirmPasswordError = false
                        )
                    )
                },
                label = {
                    Text("Password confirm")
                },
                enabled = !isSubmitForm,
                readOnly = isSubmitForm,
                placeholder = {
                    Text("Enter password again")
                },
                leadingIcon = {
                    Icon(
                        Icons.Default.Lock,
                        contentDescription = "Password"
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        if (validateForm())
                            scope.launch {
                                localFocusManager.clearFocus()
                                onSubmit()
                            }
                        else
                            handleError()
                    }
                ),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (!passwordVisible)
                        Icons.Default.Visibility
                    else
                        Icons.Default.VisibilityOff

                    val description = if (passwordVisible) "Ẩn mật khẩu" else "Hiện mật khẩu"

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, contentDescription = description)
                    }
                },
                maxLines = 1,
                singleLine = true,
                shape = MaterialTheme.shapes.large,
                isError = formError.confirmPasswordError,
                supportingText = {
                    if (formError.confirmPasswordError)
                        Text("Password does not match")
                }
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Spacer(modifier = Modifier.weight(1f))

                OutlinedButton(
                    enabled = !isSubmitForm,
                    onClick = {
                        onFormClear()
                        onClearFormError()
                        localFocusManager.clearFocus()
                    }
                ) {
                    Text("Cancel")
                }

                Spacer(modifier = Modifier.padding(MaterialTheme.dimens.small1))

                Button(
                    enabled = !isSubmitForm,
                    onClick = {
                        if (validateForm())
                            scope.launch {
                                localFocusManager.clearFocus()
                                onSubmit()
                            }
                        else
                            handleError()
                    }
                ) {
                    Text("Submit")
                }
            }
        }

        if (isSubmitForm) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        MaterialTheme.colorScheme.surface.copy(
                            alpha = 0.4f
                        )
                    ),
            )

            CircularProgressIndicator()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NonOptimizeForm() {
    var isSubmitForm by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }
    var formState by remember { mutableStateOf(FormInfo()) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    suspend fun onSubmit() {
        isSubmitForm = true
        delay(5000)
        formState = FormInfo()
        Toast.makeText(context, "Form submitted successfully", Toast.LENGTH_SHORT).show()
        isSubmitForm = false
    }

    fun checkValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun checkValidPassword(password: String): Boolean {
        return password.length >= 6
    }

    fun checkValidName(name: String): Boolean {
        return name.length >= 3
    }

    fun checkValidConfirmPassword(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword
    }

    fun validateForm(): Boolean {
        if (!checkValidEmail(formState.email)) {
            Toast.makeText(context, "Email is invalid", Toast.LENGTH_SHORT).show()
            return false
        }

        if (!checkValidName(formState.name)) {
            Toast.makeText(context, "Name is invalid", Toast.LENGTH_SHORT).show()
            return false
        }

        if (!checkValidPassword(formState.password)) {
            Toast.makeText(context, "Password is invalid", Toast.LENGTH_SHORT).show()
            return false
        }

        if (!checkValidConfirmPassword(formState.password, formState.confirmPassword)) {
            Toast.makeText(context, "Confirm password is invalid", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }


    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(MaterialTheme.dimens.medium1)
                .width(IntrinsicSize.Max),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.small1)
        ) {
            OutlinedTextField(
                value = formState.email,
                onValueChange = {
                    formState = formState.copy(
                        email = it
                    )
                },
                label = {
                    Text("Email")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                enabled = !isSubmitForm,
                readOnly = isSubmitForm,
                placeholder = {
                    Text("Enter your email")
                },
                leadingIcon = {
                    Icon(
                        Icons.Default.Email,
                        contentDescription = "Email"
                    )
                },
                maxLines = 1,
                singleLine = true,
                shape = MaterialTheme.shapes.large
            )
            OutlinedTextField(
                value = formState.name,
                onValueChange = {
                    formState = formState.copy(
                        name = it
                    )
                },
                label = {
                    Text("Name")
                },
                enabled = !isSubmitForm,
                readOnly = isSubmitForm,
                placeholder = {
                    Text("Enter your name")
                },
                leadingIcon = {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = "Name"
                    )
                },
                maxLines = 1,
                singleLine = true,
                shape = MaterialTheme.shapes.large
            )
            OutlinedTextField(
                value = formState.password,
                onValueChange = {
                    formState = formState.copy(
                        password = it
                    )
                },
                label = {
                    Text("Password")
                },
                enabled = !isSubmitForm,
                readOnly = isSubmitForm,
                placeholder = {
                    Text("Enter your password")
                },
                leadingIcon = {
                    Icon(
                        Icons.Default.Lock,
                        contentDescription = "Password"
                    )
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (!passwordVisible)
                        Icons.Default.Visibility
                    else
                        Icons.Default.VisibilityOff

                    val description = if (passwordVisible) "Ẩn mật khẩu" else "Hiện mật khẩu"

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, contentDescription = description)
                    }
                },
                maxLines = 1,
                singleLine = true,
                shape = MaterialTheme.shapes.large
            )
            OutlinedTextField(
                value = formState.confirmPassword,
                onValueChange = {
                    formState = formState.copy(
                        confirmPassword = it
                    )
                },
                label = {
                    Text("Password confirm")
                },
                enabled = !isSubmitForm,
                readOnly = isSubmitForm,
                placeholder = {
                    Text("Enter password again")
                },
                leadingIcon = {
                    Icon(
                        Icons.Default.Lock,
                        contentDescription = "Password"
                    )
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (!passwordVisible)
                        Icons.Default.Visibility
                    else
                        Icons.Default.VisibilityOff

                    val description = if (passwordVisible) "Ẩn mật khẩu" else "Hiện mật khẩu"

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, contentDescription = description)
                    }
                },
                maxLines = 1,
                singleLine = true,
                shape = MaterialTheme.shapes.large
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Spacer(modifier = Modifier.weight(1f))

                Button(
                    enabled = !isSubmitForm,
                    onClick = {
                        // validate form
                        if (!validateForm()) return@Button
                        scope.launch {
                            onSubmit()
                        }
                    }
                ) {
                    Text("Submit")
                }

                Spacer(modifier = Modifier.padding(MaterialTheme.dimens.small1))

                Button(
                    enabled = !isSubmitForm,
                    onClick = {
                        formState = FormInfo()
                    }
                ) {
                    Text("Cancel")
                }
            }
        }
    }
}