package org.example.project.presentation.viewModels

import androidx.lifecycle.ViewModel
import com.cyberarsenals.video.downloader.save.videos.domain.models.appmodels.Language
import downloaderkmpproductionapp.shared.generated.resources.Res
import downloaderkmpproductionapp.shared.generated.resources.ic_de
import downloaderkmpproductionapp.shared.generated.resources.ic_es
import downloaderkmpproductionapp.shared.generated.resources.ic_in
import downloaderkmpproductionapp.shared.generated.resources.ic_indo
import downloaderkmpproductionapp.shared.generated.resources.ic_it
import downloaderkmpproductionapp.shared.generated.resources.ic_pt
import downloaderkmpproductionapp.shared.generated.resources.ic_ru
import downloaderkmpproductionapp.shared.generated.resources.ic_us
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LanguageSelectionViewModel: ViewModel() {

    val allLanguages = listOf(
        Language("English", Res.drawable.ic_us, "en", "Select this language",
            isSelected = false,
            shadowEnabled = true,
            showAnim = true),
        Language("Russian", Res.drawable.ic_ru, "ru", "Выберите этот язык"),
        Language("Spanish", Res.drawable.ic_es, "es", "Selecciona este idioma"),
        Language("Italian", Res.drawable.ic_it, "it", "Seleziona questa lingua"),
        Language("Portuguese", Res.drawable.ic_pt, "pt", "Selecione este idioma"),
        Language("German", Res.drawable.ic_de, "de", "Wählen Sie diese Sprache"),
        Language("Hindi", Res.drawable.ic_in, "hi", "इस भाषा का चयन करें"),
        Language("Indonesian", Res.drawable.ic_indo, "id", "Pilih bahasa ini")
    )
    private val _languagesState = MutableStateFlow(allLanguages)
    val languageState = _languagesState.asStateFlow()

    var selectedLang: Language?=null

    fun onEvent(event: LanguageSelectionScreenEvents){
        when(event){
            is LanguageSelectionScreenEvents.LangItemClicked->{
                _languagesState.value = _languagesState.value.map { language ->
                    if (language.langcode == event.language.langcode) {
                        selectedLang = event.language
                        language.copy(
                            isSelected = true,
                            shadowEnabled = true,
                            showAnim = false
                        )
                    } else {
                        language.copy(
                            isSelected = false,
                            shadowEnabled = false,
                            showAnim = false
                        )
                    }
                }
            }
            is LanguageSelectionScreenEvents.FindCurrentSelectedLanguage -> {
                event.langCode?.let { langCode ->

                    _languagesState.value = _languagesState.value.map { language ->
                        if (language.langcode == langCode) {
                            language.copy(
                                isSelected = true,
                                shadowEnabled = true,
                                showAnim = false
                            )
                        } else {
                            language.copy(
                                isSelected = false,
                                shadowEnabled = false,
                                showAnim = false
                            )
                        }
                    }
                }
            }
        }
    }


    sealed interface LanguageSelectionScreenEvents{
        data class LangItemClicked(val language: Language):LanguageSelectionScreenEvents

        data class FindCurrentSelectedLanguage(val langCode: String?): LanguageSelectionScreenEvents
    }
}