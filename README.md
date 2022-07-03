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

Get a period

``` java
Day day = calendar.getDay(periodDate);
Period period = day.getPeriod();
```

Predict next period

``` java
LocalDate nextPeriodDate = calendar.getNextPeriodDate();
```