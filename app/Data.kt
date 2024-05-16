data class Admin(
    val admin_ID: Int = ID_generator.get_next_user_ID(), //class hierarchy or by handling the primary key conceptually within your application logic
    val admin_username: String = "",
    val countries: String = "",//the countries have been added for the user
    val password: String = "",
    val questionnaire: String = "",//ai questionnaire
    val user_ID: Int? = null // Provide appropriate default value or data type
                             // Nullable user_ID to represent the relationship in (user class)
)

data class User(
    var user_ID: Int = ID_generator.get_next_user_ID(), //initialy zero --> then it will increased by 1 for each user has signed up
    var chosen_country: String = "",
    var password: String = "",
    var questionnaire_result: String = "",
    var username: String = ""
)


object ID_generator {
    private var last_user_ID = 0

    fun get_next_user_ID(): Int {
        last_user_ID++
        return last_user_ID
    }
}