package ru.hse.kdz1;

/**
 * @author <a href="mailto:nstkachenko@edu.hse.ru"> Nikita Tkachenko</a>
 */
public class Bot extends Player {

    Bot(int x, int y, int money) {
        super(x, y, money);

    }

    /**
     * Переопределенный метод, возвращающий информацию о состоянии игрока
     *
     * @return информация о клетке на которой стоит игрок и балансе
     */
    @Override
    public String toStrings() {
        return "Бот находится на клетке (" + getX() + ";" + getY() + ")\n"
                + "Настоящий баланс бота: " + getMoney() +
                "\nДолг бота: " + getSumDebtor();
    }

    /**
     * Переопределенный метод, возвращающий тип игрока
     *
     * @return тип (бот)
     */
    @Override
    public String getType() {
        return "Бот \u0181 ";
    }
}
