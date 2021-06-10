package ru.hse.kdz1;

/**
 * @author <a href="mailto:nstkachenko@edu.hse.ru"> Nikita Tkachenko</a>
 */
public class Player {
    // Координата игрока на поле по x
    private int x;
    // Координата игрока на поле по y
    private int y;
    // Деньги игрока
    private int money;
    // Деньги потраченные на улучшение и покупку магазинов
    private int sumShop = 0;
    // Сумма, которую должны банку
    private int sumDebtor = 0;

    Player(int x, int y, int money) {
        this.x = x;
        this.y = y;
        this.money = money;
    }

    /**
     * Присваивает сумму долгу игроку
     *
     * @param value сумма долга
     */
    public void setSumDebtor(int value) {
        sumDebtor = value;
    }

    /**
     * Отдает сумму долга игрока
     *
     * @return сумма долга
     */
    public int getSumDebtor() {
        return sumDebtor;
    }

    /**
     * Перемещение игрока по полю
     *
     * @param sumCubes сумма, выпавшая на кубиках
     */
    public void movePlayer(int sumCubes) {

        while (sumCubes != 0) {
            if (x < Main.getWidth() - 1 && y == 0) {
                x++;
            } else if (x == Main.getWidth() - 1 && y < Main.getHeight() - 1) {
                y++;
            } else if (x > 0 && y == Main.getHeight() - 1) {
                x--;
            } else {
                y--;
            }
            sumCubes--;
        }
    }


    public int getX() {
        return x;
    }


    public int getY() {
        return y;
    }


    public int getMoney() {
        return money;
    }


    public void setMoney(int value) {
        money = value;
    }

    /**
     * Возвращает сумму
     *
     * @return сумма потраченная на магазины и их улучшения
     */
    public int getSumShop() {
        return sumShop;
    }

    /**
     * Увеличивает сумму потраченную на магазины и улучшения
     *
     * @param value стоимость очередного улучшения или цена магазина
     */
    public void setSumShop(int value) {
        sumShop += value;
    }

    public String toStrings() {
        return "";
    }

    public String getType() {
        return "";
    }
}
