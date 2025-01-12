package ru.adaliza.chatbot.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.adaliza.chatbot.dao.UserSettingsRepository;
import ru.adaliza.chatbot.model.UserSettings;
import ru.adaliza.chatbot.service.language.LanguageCode;

@ExtendWith(MockitoExtension.class)
class BaseUserSettingsServiceTest {
    @Mock private UserSettingsRepository userSettingsRepository;
    private BaseUserSettingsService userSettingsService;

    @BeforeEach
    public void setUp() {
        userSettingsService = new BaseUserSettingsService(userSettingsRepository);
    }

    @Test
    void shouldSet_userSettings() {
        when(userSettingsRepository.findById(1L)).thenReturn(Optional.empty());
        userSettingsService.setSettings(1L, LanguageCode.RU);

        verify(userSettingsRepository).save(any());
    }

    @Test
    void shouldUpdate_userSettings_ifAlreadyExists() {
        UserSettings userSettings = new UserSettings(1L, LanguageCode.EN, true);
        when(userSettingsRepository.findById(1L)).thenReturn(Optional.of(userSettings));
        userSettingsService.setSettings(1L, LanguageCode.RU);

        verify(userSettingsRepository).save(userSettings);
    }

    @Test
    void shouldReturn_useCategoryFlag() {
        when(userSettingsRepository.getUseCategoryByUserId(1L)).thenReturn(true);
        boolean useCategory = userSettingsService.useCategory(1L);

        assertThat(useCategory).isTrue();
    }

    @Test
    void shouldChange_useCategoryFlag() {
        UserSettings userSettings = new UserSettings(1L, LanguageCode.EN, true);
        when(userSettingsRepository.findById(1L)).thenReturn(Optional.of(userSettings));

        userSettingsService.changeUseCategory(1L, false);

        verify(userSettingsRepository).save(any());
    }

    @Test
    void shouldNotChange_useCategoryFlag_ifNotExists() {
        when(userSettingsRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userSettingsService.changeUseCategory(1L, false))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("User settings not found");
    }
}
