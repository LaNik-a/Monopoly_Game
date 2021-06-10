package ru.hse.kdz1;

/**
 * @author <a href="mailto:nstkachenko@edu.hse.ru"> Nikita Tkachenko</a>
 */
public class EmptyCell extends Cell {
    /**
     * Переопределенный метод, возвращающий знак нейтральной клетки
     *
     * @return |E|
     */
    @Override
    public String getSymbol() {
        return "|E|";
    }

    /**
     * Переопределенный метод, срабатывающий при попадании на нейтральную клетку
     *
     * @param player  активный игрок
     * @param player2 противник активного игрока
     */
    @Override
    public void moveAfterAction(Player player, Player player2) {
        System.out.println("\nПросто расслабься тут");
    }
}
