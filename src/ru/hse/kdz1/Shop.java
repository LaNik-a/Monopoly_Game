package ru.hse.kdz1;

import java.util.Random;

/**
 * @author <a href="mailto:nstkachenko@edu.hse.ru"> Nikita Tkachenko</a>
 */
public class Shop extends Cell {
    static Random rand = new Random();
    // Стоимость магазина
    private int N;
    // Начальная компенсация
    private int K;
    // Постоянный коэффициент компенсации за магазин
    private final double compensationCoeff;
    // Постоянный коэффициент цены повышения стоимости магазина
    private final double improvementCoeff;

    Shop(int N, int K, double compensationCoeff, double improvementCoeff) {
        this.N = N;
        this.K = K;
        this.compensationCoeff = compensationCoeff;
        this.improvementCoeff = improvementCoeff;
    }

    /**
     * Получение коэффициента компенсации
     *
     * @return постоянный коэффициент компенсации за магазин
     */
    public double getCompCef() {
        return compensationCoeff;
    }

    /**
     * Получение коэффициента цены повышения
     *
     * @return постоянный коэффициент цены повышения стоимости магазина
     */
    public double getImpCef() {
        return improvementCoeff;
    }

    /**
     * Переопределенный, метод возвращающий знак магазина никому не принадлежащего
     *
     * @return символ |S|
     */
    @Override
    public String getSymbol() {
        return "|S|";
    }

    /**
     * Переопределенный метод, срабатывающий при попадании на клетку магазина никому не принадлежащего
     * и взаимодействующий с игроком (любой игрок), попавшим на клетку для покупки или отказа
     *
     * @param player  активный игрок
     * @param player2 противник активного игрока
     */
    @Override
    public void moveAfterAction(Player player, Player player2) {
        printInfo();
        if (player.getMoney() >= N) {
            int num;
            if (player instanceof Human) {
                num = Main.getNum(0, 1, "\nВведите 1 для покупки магазина, 0 для отказа");
            } else {
                System.out.println();
                num = rand.nextInt(2);
            }
            if (num == 1) {
                System.out.println("Куплен магазин за " + N);
                player.setMoney(player.getMoney() - N);
                // Прибавляем к затраченной сумме на улучшения и покупки магазинов
                player.setSumShop(N);
                // Поменять ничейный магазин на магазин принадлежащий владельцу
                Main.changeCells(player.getX(), player.getY(), player, N, K, compensationCoeff, improvementCoeff);
            } else System.out.println("Отказался от покупки магазина");

        } else {
            System.out.println("Не хватит денег на покупку магазина");
        }


    }

    public int getN() {
        return N;
    }

    public void setN(int value) {
        N = value;
    }

    public int getK() {
        return K;
    }

    public void setK(int value) {
        K = value;
    }

    /**
     * Печатает информацию о некупленном магазине
     */
    public void printInfo() {
        System.out.println("\nЭто клетка некупленного магазина\nСтоимость магазина: " + N
                + "\nНачальная компенсация магазина: " + K + "\nПостоянный коэффициент компенсации за магазин: " + compensationCoeff
                + "\nПостоянный коэффициент повышения цены: " + improvementCoeff);
    }
}

