
package ru.hse.kdz1;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * @author <a href="mailto:nstkachenko@edu.hse.ru"> Nikita Tkachenko</a>
 */
public class Main {
    private static final double MIN_CREDIT_CEF = 0.002;
    private static final double MAX_CREDIT_CEF = 0.2;
    private static final double MIN_DEBT_CEF = 1.0;
    private static final double MAX_DEBT_CEF = 3.0;
    private static final double MIN_PENALTY_CEF = 0.01;
    private static final double MAX_PENALTY_CEF = 0.1;
    private static final int MIN_SIZE = 6;
    private static final int MAX_SIZE = 30;
    private static final int MIN_MONEY = 500;
    private static final int MAX_MONEY = 15000;
    private static final int MIN_N = 50;
    private static final int MAX_N = 500;
    private static final double MIN_COMPENSATION_CEF = 0.1;
    private static final double MAX_COMPENSATION_CEF = 1;
    private static final double MIN_IMPROVEMENT_CEF = 0.1;
    private static final double MAX_IMPROVEMENT_CEF = 2;
    private static int width;
    private static int height;
    static Scanner sc;
    static Random rand = new Random();
    static Cell[][] cells;

    public static void main(String[] args) {

        sc = new Scanner(System.in);
        if (args.length != 3) {
            System.out.println("Неверное количество аргументов !");
            return;
        }
        int money;
        try {
            height = Integer.parseInt(args[0]);
            width = Integer.parseInt(args[1]);
            money = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            System.out.println("Не все аргументы числа !");
            return;
        }

        if (height > MAX_SIZE || height < MIN_SIZE || width > MAX_SIZE
                || width < MIN_SIZE || money < MIN_MONEY || money > MAX_MONEY) {
            System.out.println("Аргументы за допустимыми пределами !");
            return;
        }
        cells = new Cell[width][height];

        double creditCoeff = getCoef(rand.nextDouble() * (MAX_CREDIT_CEF - MIN_CREDIT_CEF) + MIN_CREDIT_CEF);
        double debtCoeff = getCoef(rand.nextDouble() * (MAX_DEBT_CEF - MIN_DEBT_CEF) + MIN_DEBT_CEF);
        double penaltyCoeff = getCoef(rand.nextDouble() * (MAX_PENALTY_CEF - MIN_PENALTY_CEF) + MIN_PENALTY_CEF);
        fillMonopoly(width, 1, cells, 0, creditCoeff, debtCoeff, penaltyCoeff);
        fillMonopoly(width, height, cells, 0, creditCoeff, debtCoeff, penaltyCoeff);
        fillMonopoly(height, 1, cells, 1, creditCoeff, debtCoeff, penaltyCoeff);
        fillMonopoly(height, width, cells, 1, creditCoeff, debtCoeff, penaltyCoeff);
        fillCell(width, height, cells);
        // Создание игрока
        Human player = new Human(0, 0, money);
        // Создание бота
        Bot bot = new Bot(0, 0, money);
        // Печать поля игры
        printMonopoly(cells, player, bot);
        System.out.println("Коэффициенты:\n" + "creditCoeff: " + creditCoeff);
        System.out.println("debtCoeff: " + debtCoeff);
        System.out.println("penaltyCoeff: " + penaltyCoeff);
        // Рандомное определение кто первый ходит
        int turn = rand.nextInt(2);
        if (turn == 0) {
            System.out.println("Первым ходит игрок \u01A4");
            // Пока никто не обанкротился игра продолжается
            while (player.getMoney() >= 0 && bot.getMoney() >= 0) {
                game(player, bot);
                if (player.getMoney() < 0) break;
                game(bot, player);
            }
        } else {
            System.out.println("Первым ходит бот \u0181");
            // Пока никто не обанкротился игра продолжается
            while (player.getMoney() >= 0 && bot.getMoney() >= 0) {
                game(bot, player);
                if (bot.getMoney() < 0) break;
                game(player, bot);
            }
        }
        System.out.println("-----------------Игра окончена-----------------");
        if (player.getMoney() < 0){
            System.out.println("Победил бот :( ");
        }
        else System.out.println("Победил игрок \u263A");
    }

    /**
     * Метод хода игрока за который он перемещается по полю, взаимодействует с клеткой
     * печатает поле и всю информацию за ход
     *
     * @param player1 игрок
     * @param player2 противник игрока
     */
    private static void game(Player player1, Player player2) {
        int sumCubes = getSumCubes();
        System.out.println("\n" + player1.getType() + "выбросил на кубиках в сумме: " + sumCubes);
        player1.movePlayer(sumCubes);
        if (player1 instanceof Human) {
            printMonopoly(cells, (Human) player1, (Bot) player2);
        } else printMonopoly(cells, (Human) player2, (Bot) player1);
        cells[player1.getX()][player1.getY()].moveAfterAction(player1, player2);
        System.out.println(player1.toStrings());
    }

    /**
     * Создание магазина игрока или бота после покупки
     *
     * @param x      координата по x
     * @param y      координата по y
     * @param player игрок купивший магазин
     * @param N      стоимость магазина
     * @param K      компенсация за магазин
     */
    public static void changeCells(int x, int y, Player player, int N, int K, double compCef, double impCef) {
        if (player instanceof Human) cells[x][y] = new PlayerShop(N, K, compCef, impCef);
        else cells[x][y] = new BotShop(N, K, compCef, impCef);
    }

    /**
     * Округление постоянных коэффициентов до двух знаков после запятой
     *
     * @param coef сам коэффициент
     * @return возврат округленного
     */
    private static double getCoef(double coef) {
        BigDecimal res = new BigDecimal(coef);
        coef = Double.parseDouble(String.valueOf(res.setScale(2, RoundingMode.HALF_UP)));
        return coef;
    }

    /**
     * Печать в консоль самого игрового поля с игроками и клетками
     *
     * @param cells  двумерный массив клеток
     * @param player игрок
     * @param bot    бот
     */
    private static void printMonopoly(Cell[][] cells, Human player, Bot bot) {

        System.out.print("    ");
        for (int i = 0; i < width; i++) {
            // Нужно для красивого отображени поля так как числа с 10 занимают на 1 символ больше места
            if (i <= 9) {
                System.out.print(i + "  ");
            } else System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < height; i++) {
            // Нужно для красивого отображени поля так как числа с 10 занимают на 1 символ больше места
            if (i >= 10) {
                System.out.print(i + " ");
            } else System.out.print(i + "  ");
            for (int j = 0; j < width; j++) {
                // Отображение когда два игрока на одной клетке
                if ((j == player.getX() && i == player.getY()) && (j == bot.getX() && i == bot.getY()))
                    System.out.print("|\u2461|");
                    // Отображение позиции игрока
                else if (j == player.getX() && i == player.getY()) System.out.print("|\u01A4|");
                    // Отображение позиции бота
                else if (j == bot.getX() && i == bot.getY()) System.out.print("|\u0181|");
                else {
                    System.out.print(cells[j][i].getSymbol());
                }

            }
            System.out.println();
        }
    }

    /**
     * Метод рандомно генерирующий число на кубике и возвращающий их сумму
     *
     * @return сумма кубиков
     */
    private static int getSumCubes() {
        int firstCube = rand.nextInt(6) + 1;
        int secondCube = rand.nextInt(6) + 1;
        return firstCube + secondCube;
    }

    /**
     * Заполнение нейтральных клеток и пустых
     *
     * @param width  ширина поля
     * @param height высота поля
     * @param cells  двумерный массив клеток
     */
    private static void fillCell(int width, int height, Cell[][] cells) {
        cells[0][0] = new EmptyCell();
        cells[width - 1][0] = new EmptyCell();
        cells[width - 1][height - 1] = new EmptyCell();
        cells[0][height - 1] = new EmptyCell();
        // Заполнение пустыми клетками центр квадрата
        for (int i = 1; i < height - 1; i++) {
            for (int j = 1; j < width - 1; j++) {
                cells[j][i] = new Cell();
            }
        }
    }

    /**
     * Получение числа от пользователя в заданном отрезке
     *
     * @param leftBorder  левый конец отрезка
     * @param rightBorder правый конец отрезка
     * @param inputStr    подсказка пользователю
     * @return число от пользователя
     */
    public static int getNum(int leftBorder, int rightBorder, String inputStr) {
        if (rightBorder == 0) return 0;

        sc = new Scanner(System.in);
        String str;
        int number = -1;
        do {
            System.out.println(inputStr);
            str = sc.nextLine();
            if (isNumber(str)) {
                number = Integer.parseInt(str);
            }
        } while (!isNumber(str) || number < leftBorder || number > rightBorder);

        return number;
    }

    /**
     * Проверка на возможность преобразовать в число
     *
     * @param s строка
     * @return можно/нельзя
     */
    public static boolean isNumber(String s) {
        try {
            Integer.parseInt(s);
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    /**
     * Рандомной генерации числа
     *
     * @param numb число влияющее на генерацию
     * @return возврат сгенерированного числа
     */
    static int getRandomNumber(int numb) {
        return rand.nextInt(numb - 2) + 1;
    }

    /**
     * Заполнение самого поля разными клетками
     *
     * @param sizeFirst  первый размер поля
     * @param sizeSecond второй размер поля
     * @param cells      двумерный массив клеток
     * @param flag       для генерации боковых линий
     * @param credit     постоянный коэффициент
     * @param debt       постоянный коэффициент
     * @param penalty    постоянный коэффициент
     */
    static void fillMonopoly(int sizeFirst, int sizeSecond, Cell[][] cells, int flag, double credit, double debt, double penalty) {
        // Список свободных клеток с координатой
        ArrayList<Integer> indexFreeCellsLine = new ArrayList<>();
        int indexBank = getRandomNumber(sizeFirst);
        // Флаг 1 когда заполняем боковые
        if (flag == 1) cells[sizeSecond - 1][indexBank] = new Bank(credit, debt);
        else cells[indexBank][sizeSecond - 1] = new Bank(credit, debt);
        for (int i = 1; i < sizeFirst - 1; i++) {
            // Добавляем все индексы кроме 0 и последнего (они заняты нейтральными клетками)
            indexFreeCellsLine.add(i);
        }
        // Удаляем индекс по которому записали банк
        indexFreeCellsLine.remove(indexBank);
        int numbTaxi = rand.nextInt(3);
        for (int i = 0; i < numbTaxi; i++) {
            int randomTaxi = rand.nextInt(indexFreeCellsLine.size());
            if (flag == 1) {
                cells[sizeSecond - 1][indexFreeCellsLine.get(randomTaxi)] = new Taxi();

            } else {
                cells[indexFreeCellsLine.get(randomTaxi)][sizeSecond - 1] = new Taxi();

            }
            // Удаляем индекс по которому записали такси
            indexFreeCellsLine.remove(randomTaxi);

        }
        int numbPenalty = rand.nextInt(3);
        for (int i = 0; i < numbPenalty && indexFreeCellsLine.size() != 0; i++) {
            int randomPenalty = rand.nextInt(indexFreeCellsLine.size());
            if (flag == 1) {
                cells[sizeSecond - 1][indexFreeCellsLine.get(randomPenalty)] = new PenaltyCell(penalty);

            } else {
                cells[indexFreeCellsLine.get(randomPenalty)][sizeSecond - 1] = new PenaltyCell(penalty);

            }
            // Удаляем индекс по которому записали PenaltyCell
            indexFreeCellsLine.remove(randomPenalty);
        }
        // Оставшиеся свободные заполняем магазинами
        for (int i = 0; i < indexFreeCellsLine.size(); i++) {
            int N = rand.nextInt(MAX_N - MIN_N - 1) + MIN_N;
            int K = (int) Math.round(getCoef(rand.nextDouble() * (0.9 * N - 0.5 * N) + 0.5 * N));
            double compCef = getCoef(rand.nextDouble() * (MAX_COMPENSATION_CEF - MIN_COMPENSATION_CEF) + MIN_COMPENSATION_CEF);
            double impCef = getCoef(rand.nextDouble() * (MAX_IMPROVEMENT_CEF - MIN_IMPROVEMENT_CEF) + MIN_IMPROVEMENT_CEF);
            if (flag == 1) {
                cells[sizeSecond - 1][indexFreeCellsLine.get(i)] =
                        new Shop(N, K, compCef, impCef);

            } else {
                cells[indexFreeCellsLine.get(i)][sizeSecond - 1] = new Shop(N, K, compCef, impCef);

            }
            indexFreeCellsLine.remove(i);
            i--;
        }

    }

    /**
     * Получение ширины поля
     *
     * @return ширина
     */
    public static int getWidth() {
        return width;
    }

    /**
     * Получение длины поля
     *
     * @return длина
     */
    public static int getHeight() {
        return height;
    }

}
