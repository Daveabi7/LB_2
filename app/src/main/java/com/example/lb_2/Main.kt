fun main() {
    System.setOut(java.io.PrintStream(System.out, true, "UTF-8"))

    val menu = mapOf(
        1 to Pair("Піца", 150.0),
        2 to Pair("Бургер", 90.0),
        3 to Pair("Салат", 70.0),
        4 to Pair("Суп", 60.0),
        5 to Pair("Десерт", 50.0)
    )

    var total = 0.0

    println("Ласкаво просимо до нашої доставки!")
    println("Меню:")

    menu.forEach { (key, value) ->
        println("$key. ${value.first} - ${value.second} грн")
    }
    println("0. Завершити замовлення")

    while (true) {
        print("\nВиберіть страву (1-5) або 0 для завершення: ")

        try {
            val choice = readln().toInt()

            when (choice) {
                0 -> {
                    println("\nВаше замовлення завершено!")
                    break
                }
                in 1 .. 5 -> {
                    val menuItem = menu[choice]
                    if (menuItem != null) {
                        val dishName = menuItem.first
                        val price = menuItem.second
                        total += price
                        println("Додано: $dishName за $price грн")
                        println("Поточна сума: %.2f грн".format(total))
                    } else {
                        throw InvalidMenuChoiceException("Страва не знайдена")
                    }
                }
                else -> throw InvalidMenuChoiceException("Невірний вибір: $choice")
            }
        } catch (e: NumberFormatException) {
            println("Помилка: будь ласка, введіть число")
        } catch (e: InvalidMenuChoiceException) {
            println("Помилка: ${e.message}")
        }
    }

    val discount = when {
        total >= 500 -> 0.15
        total >= 200 -> 0.10
        else -> 0.0
    }

    println("\nПідсумок:")
    println("Сума замовлення: %.2f грн".format(total))

    if (discount > 0) {
        val discountAmount = total * discount
        println("Знижка: ${(discount * 100).toInt()}% (${"%.2f".format(discountAmount)} грн)")
        total -= discountAmount
    }

    println("До сплати: %.2f грн".format(total))
    println("Дякуємо за замовлення!")
}

class InvalidMenuChoiceException(message: String) : Exception(message)