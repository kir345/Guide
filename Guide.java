//Напишите приложение, которое будет запрашивать у пользователя следующие данные в произвольном порядке, 
//разделенные пробелом:
//Фамилия Имя Отчество датарождения номертелефона пол
//Форматы данных:
//фамилия, имя, отчество - строки
//датарождения - строка формата dd.mm.yyyy
//номер телефона - целое беззнаковое число без форматирования
//пол - символ латиницей f или m.
//Приложение должно проверить введенные данные по количеству. Если количество не совпадает с требуемым, 
//вернуть код ошибки, обработать его и показать пользователю сообщение, что он ввел меньше и больше данных, 
//чем требуется.
//Приложение должно попытаться распарсить полученные значения и выделить из них требуемые параметры. 
//Если форматы данных не совпадают, нужно бросить исключение, соответствующее типу проблемы. 
//Можно использовать встроенные типы java и создать свои. Исключение должно быть корректно обработано, 
//пользователю выведено сообщение с информацией, что именно неверно.
//Если всё введено и обработано верно, должен создаться файл с названием, равным фамилии, 
//в него в одну строку должны записаться полученные данные, вида
//<Фамилия><Имя><Отчество><датарождения> <номертелефона><пол>
//Однофамильцы должны записаться в один и тот же файл, в отдельные строки.
//Не забудьте закрыть соединение с файлом.
//При возникновении проблемы с чтением-записью в файл, исключение должно быть корректно обработано, 
//пользователь должен увидеть стектрейс ошибки.
//Данная промежуточная аттестация оценивается по системе "зачет" / "не зачет"

//"Зачет" ставится, если слушатель успешно выполнил
//"Незачет"" ставится, если слушатель успешно выполнил
//Критерии оценивания:
//Слушатель напишите приложение, которое будет запрашивать у пользователя следующие данные в произвольном порядке, 
//разделенные пробелом

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileSystemException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Guide{
    public static void main(String[] args) throws IOException{
        try{
            makeRecord();
            System.out.println("Успешно");
        }catch(FileSystemException e){
            System.out.println(e.getMessage());
        }
        catch(Exception e){
            System.out.println(e.getStackTrace());
        }
    }
    public static void makeRecord() throws Exception{
        System.out.println("Введите Фамилию Имя Отчество, дату рождения (в формате dd.mm.yyyy), номер телефона (целое безнаковое число без форматирования) и пол (символ латиницей f или m), разделенные пробелом");

        String text;
        try(BufferedReader bf = new BufferedReader(new InputStreamReader(System.in))){
            text = bf.readLine();
        }catch (IOException e){
            throw new Exception("Произошла ошибка при работе с консолью.");
        }

        String[] array = text.split(" ");
        if(array.length != 6){
            throw new Exception("Введено некоректное количество параметров.");
        }

        String surname = array[0];
        String name = array[1];
        String patronymic = array[2];
        
        SimpleDateFormat form = new SimpleDateFormat("dd.mm.yyyy");
        Date birthdate;
        try{
            birthdate = form.parse(array[3]);
        }catch (ParseException e){
            throw new ParseException("Неверный формат даты рождения", e.getErrorOffset());
        }

        int phone;
        try {
            phone = Integer.parseInt(array[4]);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Неверный формат телефона.");
        }

        String sex = array[5];
        if(!sex.toLowerCase().equals("m") && !sex.toLowerCase().equals("f")){
            throw new RuntimeException("Неправильно введен пол.");
        }

        String fileName = "files\\" + surname.toLowerCase() + ".txt";
        File file = new File(fileName);
        try(FileWriter fileWriter = new FileWriter(file, true)){
            if(file.length() > 0){
                fileWriter.write('\n');
            }
            fileWriter.write(String.format(surname,name,patronymic, form.format(birthdate), phone, sex));
        }catch (IOException e){
            throw new FileSystemException("Возникла ошибка при работе с файлом.");
        }
    }
}
