package nl.tsai.airosearch.persistence;

import nl.tsai.airosearch.airport.AirportRepository;
import nl.tsai.airosearch.country.CountryRepository;
import nl.tsai.airosearch.csv.CSVImportHistoryRepository;
import nl.tsai.airosearch.runway.RunwayRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DatabaseDataInitializerTest {

    @Mock
    private CountryRepository countryRepository;
    @Mock
    private AirportRepository airportRepository;
    @Mock
    private RunwayRepository runwayRepository;
    @Mock
    private CSVImportHistoryRepository csvImportHistoryRepository;

    @InjectMocks
    private DatabaseDataInitializer databaseDataInitializer;

    @Test
    void onApplicationReadyEvent_whenFirstApplicationRun_shouldProcessCSV() {
        final int COUNTRY_BATCHES = 1;
        final int AIRPORT_BATCHES = 59;
        final int AIRPORTS = 58920;
        final int RUNWAY_BATCHES = 42;
        final int RUNWAYS = 41730;

        databaseDataInitializer.onApplicationReadyEvent();

        verify(csvImportHistoryRepository, times(3)).existsByPath(anyString());
        verify(csvImportHistoryRepository, times(3)).save(any());
        verifyNoMoreInteractions(csvImportHistoryRepository);

        verify(countryRepository, times(COUNTRY_BATCHES)).saveAll(any());
        verify(countryRepository, times(AIRPORTS)).findByCode(any());
        verifyNoMoreInteractions(countryRepository);

        verify(airportRepository, times(AIRPORT_BATCHES)).saveAll(any());
        verify(airportRepository, times(RUNWAYS)).findOneById(any());
        verifyNoMoreInteractions(airportRepository);

        verify(runwayRepository, times(RUNWAY_BATCHES)).saveAll(any());
        verifyNoMoreInteractions(runwayRepository);
    }

    @Test
    void onApplicationReadyEvent_whenConsecutiveApplicationRuns_shouldNotProcessCSV() {
        when(csvImportHistoryRepository.existsByPath(anyString())).thenReturn(true);

        databaseDataInitializer.onApplicationReadyEvent();

        verify(csvImportHistoryRepository, times(3)).existsByPath(anyString());
        verifyNoMoreInteractions(csvImportHistoryRepository);

        verifyNoInteractions(countryRepository);
        verifyNoInteractions(airportRepository);
        verifyNoInteractions(runwayRepository);
    }
}