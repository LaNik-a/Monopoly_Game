package ru.hse.kdz1;

/**
 * @author <a href="mailto:nstkachenko@edu.hse.ru"> Nikita Tkachenko</a>
 */
public class Human extends Player {


    Human(int x, int y, int money) {
        super(x, y, money);
    }

    /**
     * Переопределенный метод, возвращающий информацию о состоянии игрока
     *
     * @return информация о клетке на которой стоит игрок и балансе
     */
    @Override
    public String toStrings() {
        return "Ты находишься на клетке (" + getX() + ";" + getY() + ")\n" +
                "Настоящий баланс игрока: " + getMoney() +
                "\nДолг игрока: " + getSumDebtor();
    }

    /**
     * Переопределенный метод, возвращающий тип игрока
     *
     * @return тип(игрок)
     */
    @Override
    public String getType() {
        return "Игрок \u01A4 ";
    }


}
