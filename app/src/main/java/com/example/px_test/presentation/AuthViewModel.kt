import androidx.lifecycle.ViewModel
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCase: AuthUseCase
) : ViewModel() {
    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    fun sendAuthData(token: String, userData: VKIDUser) {
        viewModelScope.launch {
        }
    }
    fun notifyError(error: VKIDAuthFail) {
        _authState.value = AuthState.Error(error.description)
    }
}
sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Success : AuthState()
    data class Error(val message: String) : AuthState()
}