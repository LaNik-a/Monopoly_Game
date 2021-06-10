package ru.hse.kdz1;

import java.util.Random;

/**
 * @author <a href="mailto:nstkachenko@edu.hse.ru"> Nikita Tkachenko</a>
 */
public class Taxi extends Cell {
    static Random rand = new Random();


    /**
     * Переопределенный метод, возвращающий знак такси
     *
     * @return возвращает |T|
     */
    @Override
    public String getSymbol() {
        return "|T|";
    }

    /**
     * Переопределенный метод, срабатывающий при попадании на клетку такси и взаимодействующий
     * с пользователем через консоль
     *
     * @param player  активный игрок
     * @param player2 противник активного игрока
     */
    @Override
    public void moveAfterAction(Player player, Player player2) {
        // Пределы для рандома заданы в условии
        int taxiDistance = rand.nextInt(3) + 3;
        System.out.println("\n Перемещение на " + taxiDistance + " клеток!");
        // Перемещение пользователя на сгенерированное число клеток
        player.movePlayer(taxiDistance);
        // Вызов метода при наступлении на клетку
        Main.cells[player.getX()][player.getY()].moveAfterAction(player, player2);
    }
}
