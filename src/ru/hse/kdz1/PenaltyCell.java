package ru.hse.kdz1;

/**
 * @author <a href="mailto:nstkachenko@edu.hse.ru"> Nikita Tkachenko</a>
 */
public class PenaltyCell extends Cell {
    // Постоянный коэффициент для списания денег
    private final double penaltyCoeff;

    PenaltyCell(double penaltyCoeff) {
        this.penaltyCoeff = penaltyCoeff;
    }

    /**
     * Переопределенный метод, возвращающий знак штрафной клетки
     *
     * @return |%|
     */
    @Override
    public String getSymbol() {
        return "|%|";
    }

    /**
     * Переопределенный метод, срабатывающий при попадании на штрафную клетку
     *
     * @param player  активный игрок
     * @param player2 противник активного игрока
     */
    @Override
    public void moveAfterAction(Player player, Player player2) {
        if (player instanceof Human)
            System.out.println("\nПосле попадания на PenaltyCell списалось с игрока " +
                    (int) Math.round(penaltyCoeff * player.getMoney()));
        else System.out.println("\nПосле попадания на PenaltyCell списалось с бота " +
                (int) Math.round(penaltyCoeff * player.getMoney()));
        // Списание денег с игрока по формуле
        player.setMoney(player.getMoney() - (int) Math.round(penaltyCoeff * player.getMoney()));
    }
}
