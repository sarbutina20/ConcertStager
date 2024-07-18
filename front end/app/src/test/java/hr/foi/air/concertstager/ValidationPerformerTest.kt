package hr.foi.air.concertstager

import hr.foi.air.concertstager.core.login.Validator
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ValidationPerformerTest {
    @Test
    fun validateName_StringContainsNumbers_InvalidName() {
        val response = Validator.validateName("Karlo123")
        assertFalse(response)
    }

    @Test
    fun validateName_StringContainsOnlyLetters_ValidName(){
        val response = Validator.validateName("Karlo")
        assertTrue(response)
    }

    @Test
    fun validateEmail_WrongEmailFormat_InvalidEmail(){
        val response = Validator.validateEmail("jasamemail.com")
        assertFalse(response)
    }

    @Test
    fun validateEmail_GoodEmailFormat_ValidEmail(){
        val response = Validator.validateEmail("karlo@gmail.com")
        assertTrue(response)
    }

    @Test
    fun validatePassword_PasswordContainsOnlyLetters_InvalidPassword(){
        val response = Validator.validatePassword("jasamkarlo")
        assertFalse(response)
    }

    @Test
    fun validatePassword_PasswordHasLessThan8Characters_InvalidPassword(){
        val response = Validator.validatePassword("jasa123")
        assertFalse(response)
    }

    @Test
    fun validatePassword_PasswordHasLettersAndNumbersAnd8Characters_ValidPassword(){
        val response = Validator.validatePassword("jasamkarlo123")
        assertTrue(response)
    }

    @Test
    fun validateContactNumber_ContainsLetters_InvalidPhoneNumber(){
        val response = Validator.validateContactNumber("092122klkkl1")
        assertFalse(response)
    }

    @Test
    fun validateContactNumber_LengthIs6Characters_InvalidPhoneNumber(){
        val response = Validator.validateContactNumber("097212")
        assertFalse(response)
    }

    @Test
    fun validateContactNumber_NormalPhoneNumberIsUsed_ValidPhoneNumber(){
        val response = Validator.validateContactNumber("0821252912")
        assertTrue(response)
    }

}