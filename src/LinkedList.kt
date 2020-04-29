import java.util.*

fun main() {
    val listOfPets = LinkedList<String>()

    do {
        print("Digite o nome do seu animal: ")
        val petName = readLine()!!.toString()
        listOfPets.add(petName)
    } while (petName != "quit")

    for (pet in listOfPets) {
        println("Pet: $pet")
    }
}