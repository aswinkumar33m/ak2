import java.util.ArrayList;
import java.util.List;

// Observer Pattern
interface Observer {
    void update(String weatherData);
}

class WeatherStation {
    private List<Observer> observers = new ArrayList<>();
    private String weatherData;

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void setWeatherData(String data) {
        this.weatherData = data;
        notifyObservers();
    }

    private void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(weatherData);
        }
    }
}

class WeatherDisplay implements Observer {
    @Override
    public void update(String weatherData) {
        System.out.println("Weather Display: " + weatherData);
    }
}

// Strategy Pattern
interface ReportingStrategy {
    void report(String weatherData);
}

class SimpleReport implements ReportingStrategy {
    @Override
    public void report(String weatherData) {
        System.out.println("Simple Report: " + weatherData);
    }
}

class DetailedReport implements ReportingStrategy {
    @Override
    public void report(String weatherData) {
        System.out.println("Detailed Report: Detailed analysis of " + weatherData);
    }
}

class ReportingContext {
    private ReportingStrategy strategy;

    public ReportingContext(ReportingStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(ReportingStrategy strategy) {
        this.strategy = strategy;
    }

    public void report(String weatherData) {
        strategy.report(weatherData);
    }
}

// Singleton Pattern
class WeatherDataManager {
    private static WeatherDataManager instance;
    private List<String> weatherData = new ArrayList<>();

    private WeatherDataManager() {}

    public static WeatherDataManager getInstance() {
        if (instance == null) {
            instance = new WeatherDataManager();
        }
        return instance;
    }

    public void addWeatherData(String data) {
        weatherData.add(data);
    }

    public List<String> getWeatherData() {
        return weatherData;
    }
}

// Factory Method Pattern
abstract class WeatherReport {
    public abstract void generate();
}

class TextReport extends WeatherReport {
    @Override
    public void generate() {
        System.out.println("Generating text weather report.");
    }
}

class GraphicalReport extends WeatherReport {
    @Override
    public void generate() {
        System.out.println("Generating graphical weather report.");
    }
}

abstract class ReportCreator {
    public abstract WeatherReport createReport();
}

class TextReportCreator extends ReportCreator {
    @Override
    public WeatherReport createReport() {
        return new TextReport();
    }
}

class GraphicalReportCreator extends ReportCreator {
    @Override
    public WeatherReport createReport() {
        return new GraphicalReport();
    }
}

// Adapter Pattern
interface WeatherSource {
    String fetchData();
}

class ApiWeatherSource implements WeatherSource {
    @Override
    public String fetchData() {
        return "API Weather Data";
    }
}

class SensorWeatherSource implements WeatherSource {
    @Override
    public String fetchData() {
        return "Sensor Weather Data";
    }
}

class WeatherSourceAdapter implements WeatherSource {
    private WeatherSource weatherSource;

    public WeatherSourceAdapter(WeatherSource weatherSource) {
        this.weatherSource = weatherSource;
    }

    @Override
    public String fetchData() {
        return weatherSource.fetchData();
    }
}

// Decorator Pattern
class Report {
    private String content;

    public Report(String content) {
        this.content = content;
    }

    public String render() {
        return content;
    }
}

abstract class ReportDecorator extends Report {
    protected Report wrapped;

    public ReportDecorator(Report wrapped) {
        super(wrapped.render());
        this.wrapped = wrapped;
    }

    public abstract String render();
}

class HeaderDecorator extends ReportDecorator {
    public HeaderDecorator(Report wrapped) {
        super(wrapped);
    }

    @Override
    public String render() {
        return "Weather Report Header\n" + wrapped.render();
    }
}

class FooterDecorator extends ReportDecorator {
    public FooterDecorator(Report wrapped) {
        super(wrapped);
    }

    @Override
    public String render() {
        return wrapped.render() + "\nWeather Report Footer";
    }
}

class TimestampDecorator extends ReportDecorator {
    public TimestampDecorator(Report wrapped) {
        super(wrapped);
    }

    @Override
    public String render() {
        return wrapped.render() + "\nTimestamp: " + System.currentTimeMillis();
    }
}

// Combined Use Case
public class AdvancedWeatherStation {
    public static void main(String[] args) {
        // Observer Pattern
        WeatherStation weatherStation = new WeatherStation();
        WeatherDisplay weatherDisplay = new WeatherDisplay();
        weatherStation.addObserver(weatherDisplay);

        // Singleton Pattern
        WeatherDataManager dataManager = WeatherDataManager.getInstance();
        dataManager.addWeatherData("Sunny, 25°C");
        dataManager.addWeatherData("Cloudy, 20°C");

        // Factory Method Pattern
        ReportCreator textReportCreator = new TextReportCreator();
        ReportCreator graphicalReportCreator = new GraphicalReportCreator();

        WeatherReport textReport = textReportCreator.createReport();
        WeatherReport graphicalReport = graphicalReportCreator.createReport();

        textReport.generate();
        graphicalReport.generate();

        // Strategy Pattern
        ReportingContext reportingContext = new ReportingContext(new SimpleReport());
        reportingContext.report("Sunny, 25°C");

        reportingContext.setStrategy(new DetailedReport());
        reportingContext.report("Cloudy, 20°C");

        // Adapter Pattern
        WeatherSource apiSource = new ApiWeatherSource();
        WeatherSource sensorSource = new SensorWeatherSource();

        WeatherSourceAdapter apiAdapter = new WeatherSourceAdapter(apiSource);
        WeatherSourceAdapter sensorAdapter = new WeatherSourceAdapter(sensorSource);

        System.out.println(apiAdapter.fetchData());
        System.out.println(sensorAdapter.fetchData());

        // Decorator Pattern
        Report simpleReport = new Report("Weather: Sunny, 25°C");
        Report headerReport = new HeaderDecorator(simpleReport);
        Report footerReport = new FooterDecorator(headerReport);
        Report timestampedReport = new TimestampDecorator(footerReport);

        System.out.println(simpleReport.render());
        System.out.println(headerReport.render());
        System.out.println(footerReport.render());
        System.out.println(timestampedReport.render());

        // Observer Pattern - Notify about current weather data
        weatherStation.setWeatherData("Sunny, 25°C");
        weatherStation.setWeatherData("Cloudy, 20°C");
    }
}