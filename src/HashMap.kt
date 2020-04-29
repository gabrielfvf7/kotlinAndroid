fun main() {
    var listOfUsers = HashMap<Int,String>()
    listOfUsers[123] = "Gabriel"
    listOfUsers[12] = "Leia"

    println("Print ID 123: ${listOfUsers[123]}")
    for (key in listOfUsers.keys) {
        println("$key: ${listOfUsers[key]}")
    }
}