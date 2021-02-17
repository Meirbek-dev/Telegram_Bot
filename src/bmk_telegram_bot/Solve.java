package bmk_telegram_bot;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Solve extends BotCommand {

    public Solve() {
        super("solve", "calculate");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {

        String chatId = chat.getId().toString();

        if (arguments != null && arguments.length == 3) {
            SendMessage answer = new SendMessage();
            answer.setChatId(chatId);
            answer.setParseMode(ParseMode.MARKDOWN);
            try {
                StringBuilder messageTextBuilder = new StringBuilder("`x = ");

                double x = Double.parseDouble(arguments[0]);
                messageTextBuilder.append(arguments[0]).append("`\n");

                double a = Double.parseDouble(arguments[1]);
                messageTextBuilder.append("`a = ").append(arguments[1]).append("`\n");

                double b = Double.parseDouble(arguments[2]);
                messageTextBuilder.append("`b = ").append(arguments[2]).append("`\n\n");

                double result = solve(x, a, b);
                messageTextBuilder.append("*y = ").append(result).append("*");
                answer.setText(messageTextBuilder.toString());

            } catch (NumberFormatException e) {
                answer.setText("*Аргументы введены неправильно.*");
            }

            try {
                absSender.execute(answer);
            } catch (TelegramApiException e) {
            }

        } else {
            SendPhoto answer = TelegramBot.getDefaultMessage(chatId);
            try {
                absSender.execute(answer);
            } catch (TelegramApiException e) {
            }
        }
    }

    private double solve(double x, double a, double b) {

        double result;

        if (x >= 5) {
            result = (5 * x * x) / (6 * (a + b) * (a + b));
        } else {
            result = x * x * x * (a + b);
        }

        return result;
    }
}
