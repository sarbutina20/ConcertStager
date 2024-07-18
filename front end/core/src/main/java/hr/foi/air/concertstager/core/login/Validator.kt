package hr.foi.air.concertstager.core.login

class Validator {
    
    companion object{
        private const val NAME_REGEX = "^[a-zA-Z ]+\$"
        private const val EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\$"
        private const val PHONE_REGEX = "^[0-9]{8,10}$"  // Prilagodite prema potrebi
        private const val PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$"
        private const val COMMENT_REGEX = "^[\\s\\S]{3,256}$"

        fun validateName(name: String) : Boolean {
            return name.matches(NAME_REGEX.toRegex())
        }

        fun validateEmail(email: String) : Boolean {
            return email.matches(EMAIL_REGEX.toRegex())
        }

        fun validateContactNumber(contactNumber: String) : Boolean {
            return contactNumber.matches(PHONE_REGEX.toRegex())
        }

        fun validatePassword(password: String) : Boolean {
            return password.matches(PASSWORD_REGEX.toRegex())
        }

        fun validateComment(comment: String) : Boolean {
            return comment.matches(COMMENT_REGEX.toRegex())
        }
    }
}