package ru.hse.kdz1;

/**
 * @author <a href="mailto:nstkachenko@edu.hse.ru"> Nikita Tkachenko</a>
 */
public class Bank extends Cell {
    // Постоянный коэффициент для взятие кредита
    private final double creditCoeff;
    // Постоянный коэффициент для долга по кредиту
    private final double debtCoeff;

    Bank(double creditCoeff, double debtCoeff) {
        this.creditCoeff = creditCoeff;
        this.debtCoeff = debtCoeff;
    }

    /**
     * Переопределенный метод, возвращающий знак банка
     *
     * @return возвращает $
     */
    @Override
    public String getSymbol() {
        return "|$|";
    }

    /**
     * Переопределенный метод, срабатывающий при попадании на клетку банка и взаимодействующий
     * с пользователем через консоль
     *
     * @param player  активный игрок
     * @param player2 противник активного игрока
     */
    @Override
    public void moveAfterAction(Player player, Player player2) {
        if (player instanceof Human) {
            if (player.getSumDebtor() > 0) {
                player.setMoney(player.getMoney() - player.getSumDebtor());
                System.out.println("\nИгрок попал на клетку банка и выплатил ему " + player.getSumDebtor());
                player.setSumDebtor(0);

            } else if (player.getSumShop() > 0) {
                // Максимальная сумма, которую можно взять в долг
                int maxSumBank = (int) Math.round(creditCoeff * player.getSumShop());
                // Сумма взятая в долг
                int sumBank = Main.getNum(0, maxSumBank,
                        "Введите сумму которую хотите в долг из промежутка [0;" + maxSumBank + "]");
                player.setMoney(player.getMoney() + sumBank);
                player.setSumDebtor((int) Math.round(sumBank * debtCoeff));
                if (sumBank > 0)
                    System.out.println("Теперь при попадании клетку банка игрок выплачивает: " + player.getSumDebtor());
            }
        } else System.out.println("\nБот не может пользоваться услугами банка");
    }
}
