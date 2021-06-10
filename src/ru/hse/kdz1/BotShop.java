package ru.hse.kdz1;

/**
 * @author <a href="mailto:nstkachenko@edu.hse.ru"> Nikita Tkachenko</a>
 */
public class BotShop extends Shop {


    BotShop(int N, int K, double compensationCoeff, double improvementCoeff) {
        super(N, K, compensationCoeff, improvementCoeff);

    }

    /**
     * Переопределенный, метод возвращающий знак магазина противника
     *
     * @return символ |O|
     */
    @Override
    public String getSymbol() {
        return "|O|";
    }

    /**
     * Переопределенный метод, срабатывающий при попадании на клетку магазина противника
     * и взаимодействующий с игроком, попавшим на клетку
     *
     * @param player  активный игрок
     * @param player2 противник активного игрока
     */
    @Override
    public void moveAfterAction(Player player, Player player2) {
        // Если игрок попавший на магазин бота оказался сам бот
        if (player instanceof Bot) {
            int random = rand.nextInt(2);
            if (random == 1 && player.getMoney() >= (int) Math.round(getN() + getImpCef() * getN())) {
                // Повышение цены магазина
                setN((int) Math.round(getN() + getImpCef() * getN()));
                // Увеличение компенсации
                setK((int) Math.round(getK() + getK() * getCompCef()));
                // Прибавляем к затраченной сумме на улучшения магазина
                player.setSumShop(getN());
                System.out.println("Бот потратил на улучшение: " + getN());
                // Улучшаем магазин
                player.setMoney(player.getMoney() - getN());
            } else {
                if (random == 0) System.out.println("Бот прошел мимо");
                else System.out.println("Боту не хватает денег");
            }
        } else {

            System.out.println("Игрок платит компенсацию-->" + getK());
            // Игрок платит компенсацию за остановку на чужом магазине
            player.setMoney(player.getMoney() - getK());
            // Бот получает компенсацию
            player2.setMoney(player2.getMoney() + getK());
        }


    }

    /**
     * Печатает информацию о магазине бота
     */
    @Override
    public void printInfo() {
        System.out.println("\nЭто клетка магазина бота\n" + "Стоимость улучшения магазина: " + getN()
                + "Компенсация за магазин: " + getK());
    }
}

