# Orchid
### 100% offline women's health app

### Usage

Create a calendar

``` java
PeriodCalendar calendar = new PeriodCalendar(new PeriodPredictor());
```

Add a period

``` java
LocalDate periodDate = LocalDate.now();
calendar.addPeriod(periodDate);
```

Add a period with flow

``` java
LocalDate periodDate = LocalDate.now();
calendar.addPeriod(periodDate, Flow.LIGHT);
```

Get a period

``` java
Day day = calendar.getDay(periodDate);
Period period = day.getPeriod();
```

Get a period with flow

``` java
Day day = calendar.getDay(periodDate);
Period period = day.getPeriod();
Flow flow = period.getFlow();
```

Predict next period window

``` java
PeriodWindow periodWindow = periodCalendar.getNextPeriodWindow();
boolean isEmpty = periodWindow.isEmpty();

LocalDate startDate = periodWindow.getStartDate();
List<LocalDate> dates = periodWindow.getDates();
```

Predict next ovulation date

``` java
LocalDate nextOvulationDate = calengar.getNextOvulationDate();
```

Add temperature

``` java
Temperature temperature = new Temperature(new BigDecimal("98.6"), Metric.FAHRENHEIT);
calendar.addTemperature(date, temperature);

Temperature retrievedTemperature = calendar.getDay(date).getTemperature();
```