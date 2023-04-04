package com.cafesa.ej1_myinfo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cafesa.ej1_myinfo.databinding.ActivityResultsBinding
import java.util.*

class Results : AppCompatActivity() {

    private lateinit var binding: ActivityResultsBinding

    private val careers = arrayOf(
        R.drawable.ing_aeroespacial,
        R.drawable.ing_ambiental,
        R.drawable.ing_biomedicos,
        R.drawable.ing_civil,
        R.drawable.ing_computacion,
        R.drawable.ing_electrica,
        R.drawable.ing_geofisica,
        R.drawable.ing_geologica,
        R.drawable.ing_geomatica,
        R.drawable.ing_industrial,
        R.drawable.ing_mecanica,
        R.drawable.ing_mecatronica,
        R.drawable.ing_minas,
        R.drawable.ing_petrolera,
        R.drawable.ing_telecom
    )

    private val animals = arrayOf(
        R.drawable.lunar_rat,
        R.drawable.lunar_ox,
        R.drawable.lunar_tiger,
        R.drawable.lunar_rabbit,
        R.drawable.lunar_dragon,
        R.drawable.lunar_snake,
        R.drawable.lunar_horse,
        R.drawable.lunar_goat,
        R.drawable.lunar_monkey,
        R.drawable.lunar_rooster,
        R.drawable.lunar_dog,
        R.drawable.lunar_pig
    )

    private val zodiac = arrayOf(
        R.drawable.zodiac_capricorn,
        R.drawable.zodiac_aquarius,
        R.drawable.zodiac_pisces,
        R.drawable.zodiac_aries,
        R.drawable.zodiac_taurus,
        R.drawable.zodiac_gemini,
        R.drawable.zodiac_cancer,
        R.drawable.zodiac_leo,
        R.drawable.zodiac_virgo,
        R.drawable.zodiac_libra,
        R.drawable.zodiac_scorpio,
        R.drawable.zodiac_sagittarius
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityResultsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras

        if(bundle!=null){
            val name = bundle.getString("name","Charlie")
            val lastName = bundle.getString("lastName","Brown")
            val year = bundle.getInt("year",1950)
            val month = bundle.getInt("month",10)
            val day = bundle.getInt("day",2)
            val mail = bundle.getString("mail","default@mail.com")
            val number = bundle.getInt("number",123456789)
            val career = bundle.getInt("career",0)


            binding.tvName.text = getString(R.string.res_name,name,lastName)
            binding.tvMail.text = getString(R.string.res_mail,mail)
            binding.tvNumber.text = getString(R.string.res_number,number)
            //This calculates the age of the user
            binding.tvAge.text = getString(R.string.res_age,calculateAge(day,month,year))
            //This sets the lunar new year animal image
            //val animals = listOf("Rat", "Ox", "Tiger", "Rabbit", "Dragon", "Snake", "Horse", "Goat", "Monkey", "Rooster", "Dog", "Pig")
            val animalsStr = resources.getStringArray(R.array.lunar_animals)
            val animalIdx = (year - 4) % 12
            binding.tvAnimal.text = getString(R.string.res_animal,animalsStr[animalIdx])
            animals[animalIdx].let { binding.ivLunar.setImageResource(it) }
            //This sets the zodiac sign image
            val zodiacStr = resources.getStringArray(R.array.zodiac_signs)
            val zodiacIdx = getZodiacSign(day,month+1)
            binding.tvZodiac.text = getString(R.string.res_zodiac,zodiacStr[zodiacIdx])
            zodiac[zodiacIdx].let { binding.ivZodiac.setImageResource(it) }
            //This sets the image source to an image view
            val careersStr = resources.getStringArray(R.array.form_careers)
            binding.tvCareer.text = getString(R.string.res_career,careersStr[career])
            careers[career].let { binding.ivCareer.setImageResource(it) }

        }
    }
}

fun calculateAge(day: Int, month: Int, year: Int): Int {
    val currDate = Calendar.getInstance()
    val birthDate = Calendar.getInstance()

    birthDate.set(year, month, day)

    var age = currDate.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR)

    if (currDate.get(Calendar.DAY_OF_YEAR) < birthDate.get(Calendar.DAY_OF_YEAR)) {
        age--
    }

    return age
}

fun getZodiacSign(day: Int, month: Int): Int {
    //val signs = listOf("Capricorn", "Aquarius", "Pisces", "Aries", "Taurus", "Gemini", "Cancer", "Leo", "Virgo", "Libra", "Scorpio", "Sagittarius")
    val days = listOf(20, 19, 21, 20, 21, 21, 23, 23, 23, 23, 22, 22)
    if (day <= days[month - 1]) {
        return month - 1
    } else {
        return month % 12
    }
}