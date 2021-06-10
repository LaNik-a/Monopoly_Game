package ru.hse.kdz1;


/**
 * @author <a href="mailto:nstkachenko@edu.hse.ru"> Nikita Tkachenko</a>
 */
public class PlayerShop extends Shop {


    PlayerShop(int N, int K, double compensationCoeff, double improvementCoeff) {
        super(N, K, compensationCoeff, improvementCoeff);

    }

    /**
     * Переопределенный, метод возвращающий знак магазина игрока
     *
     * @return символ |M|
     */
    @Override
    public String getSymbol() {
        return "|M|";
    }

    /**
     * Переопределенный метод, срабатывающий при попадании на клетку магазина игрока (не бота)
     * и взаимодействующий с игроком (любой игрок), попавшим на клетку
     *
     * @param player  активный игрок
     * @param player2 противник активного игрока
     */
    @Override
    public void moveAfterAction(Player player, Player player2) {
        int num;
        if (player instanceof Human && player.getMoney() >= (int) Math.round(getN() + getImpCef() * getN())) {
            String str = "Введите 1 для улучшения магазина за " +
                    (int) Math.round(getN() + getImpCef() * getN()) + ", 0 для отказа";
            num = Main.getNum(0, 1, str);
            if (num == 1) {
                // Повышение цены магазина
                setN((int) Math.round(getN() + getImpCef() * getN()));
                // Увеличение компенсации
                setK((int) Math.round(getK() + getK() * getCompCef()));
                // Прибавляем к затраченной сумме на улучшения магазина
                player.setSumShop(getN());
                System.out.println("Игрок потратил на улучшение: " + getN());
                player.setMoney(player.getMoney() - getN());
            } else System.out.println("Игрок прошел мимо");
        } else {
            if (player instanceof Bot) {
                // Бот платит компенсацию за остановку на чужом магазине
                player.setMoney(player.getMoney() - getK());
                System.out.println("Бот заплатил компенсацию-->" + getK());
                // Игрок получает компенсацию
                player2.setMoney(player2.getMoney() + getK());
            } else System.out.println("Игроку не хватает денег");

        }


    }

    /**
     * Печатает информацию о магазине игрока
     */
    @Override
    public void printInfo() {
        System.out.println("\nЭто клетка магазина игрока\n" + "Стоимость улучшения магазина: " + getN()
                + "Компенсация за магазин: " + getK());
    }
}
