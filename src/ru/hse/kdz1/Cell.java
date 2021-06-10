package ru.hse.kdz1;

/**
 * @author <a href="mailto:nstkachenko@edu.hse.ru"> Nikita Tkachenko</a>
 */
public class Cell {
    /**
     * Метод возвращающий символ клетки внутри поля
     *
     * @return |■|
     */
    public String getSymbol() {
        return "|■|";
    }

    /**
     * Метод срабатывающий при попадании на клетку
     *
     * @param player  активный игрок
     * @param player2 соперник активного игрока
     */
    public void moveAfterAction(Player player, Player player2) {
        System.out.println("\nТы сюда не должен был наступать");
    }

}
