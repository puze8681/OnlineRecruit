package kr.puze.onlinerecruit.Data

data class MainData(var type: String, var user_image: String, var user_name: String, var repo_name: String, var repo_description: String, var repo_star: String) {
    fun type(): String {
        return type
    }

    fun userImage(): String {
        return user_image
    }

    fun userName(): String {
        return user_name
    }

    fun repoName(): String {
        return repo_name
    }

    fun repoDescription(): String {
        return repo_description
    }

    fun repoStar(): String {
        return repo_star
    }
}