fun main() {
    print("Coloque seu nome: ")
    val name: String = readLine()!!
    print("Coloque sua idade: ")
    val age: Int = readLine()!!.toInt()
    print("Coloque seu GPA: ")
    val GPA: Double = readLine()!!.toDouble()

    println("Nome: $name")
    println("Idade: $age")
    println("GPA: $GPA")


}