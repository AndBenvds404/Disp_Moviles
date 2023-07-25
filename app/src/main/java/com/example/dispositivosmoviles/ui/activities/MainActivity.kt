package com.example.dispositivosmoviles.ui.activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.SearchManager
import android.content.ContentProviderClient
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import android.content.Context
import android.location.Geocoder
import android.location.Location
import android.os.Looper
import android.speech.RecognizerIntent
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult.*
import androidx.core.content.PermissionChecker.PermissionResult

import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.databinding.ActivityMainBinding
import com.example.dispositivosmoviles.ui.validator.LoginValidator
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority


import com.google.android.material.snackbar.Snackbar
import java.util.Locale
import kotlin.math.log


val Context.dataStore: DataStore<Preferences>
        by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

    //Para enalzar y utilizar el Binding
    private lateinit var binding: ActivityMainBinding
    private lateinit var fusedLocationProviderClient:FusedLocationProviderClient
    private lateinit var locationRquest: LocationRequest
    private lateinit var locationCallback: LocationCallback


        @SuppressLint("MissingPermission")
        private val locationContract = registerForActivityResult(ActivityResultContracts.RequestPermission()){
                isGranted->


               when (isGranted){

               true ->{

                   val task = fusedLocationProviderClient.lastLocation
                   task.addOnSuccessListener { location ->
                       fusedLocationProviderClient.requestLocationUpdates(
                           locationRquest,
                           locationCallback,
                           Looper.getMainLooper()

                       )
                   }
                       /* task.addOnSuccessListener {
                            var alert = androidx.appcompat.app.AlertDialog.Builder(this )
                            alert.apply{
                                setTitle("Titulo alerta")
                                setMessage("Existe un problema con el sistema de posicionacmiento")
                                setPositiveButton("ok"){dialog,id->dialog.dismiss()
                                }
                                setCancelable(false)
                        }.create()
                            alert.show()
                   }*/

               }
                   shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION
                   )->{
                       Snackbar.make(binding.txtName,
                           "ayudame a ayudarte",
                           Snackbar.LENGTH_LONG)
                           .show()

                   }

                     false->{  Snackbar.make(binding.txtName,
                       "denegado permiso",
                       Snackbar.LENGTH_LONG)
                       .show()
                 }


           }
    }


    val speechToText = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){
            activityResult->

        val sn = Snackbar.make(binding.txtName, "", Snackbar.LENGTH_LONG)
        var message = ""
        when(activityResult.resultCode){
            RESULT_OK->{
                val msg = activityResult.data?.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS).toString()

                if(msg.isNotEmpty()){
                    val intent = Intent(
                        Intent.ACTION_WEB_SEARCH,
                    )
                    intent.setClassName(
                        "com.google.android.googlequicksearchbox",
                        "com.google.android.googlequicksearchbox.SearchActivity"
                    )
                    intent.putExtra(SearchManager.QUERY,msg)
                    startActivity(intent)
                }
            }

            RESULT_CANCELED->{

            }else ->{
            message = "ocurrio un eerro"
            sn.setBackgroundTint(resources.getColor(R.color.orange))
        }
        }
        sn.setText(message)
        sn.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Los botones con binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        locationRquest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,1000
        ).build()

        locationCallback = object : LocationCallback(){
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)

                if(locationResult!=null){
                    locationResult.locations.forEach{location ->
                        Log.d("uce","ubicacion: ${location.latitude}," +
                                "                       "+"${location.longitude}")
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

    }

    override fun onPause() {
        super.onPause()
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    @SuppressLint("MissingPermission")
    private fun initClass() {

        binding.btnLogin.setOnClickListener {

            val check = LoginValidator().checkLogin(
                binding.txtName.text.toString(), binding.txtPass.text.toString()
            )

            if(check){

                lifecycleScope.launch(Dispatchers.IO){
                    saveDataStore(binding.txtName.text.toString())
                }

                var intent = Intent(this, PrincipalActivity::class.java)
                intent.putExtra("var1", binding.txtName.text.toString())
                intent.putExtra("var2", 2)
                startActivity(intent)
            }else{
                var snackbar = Snackbar.make(binding.txtTitle,
                    "Usuario o contraseña inválidos",
                    Snackbar.LENGTH_LONG)
                //snackbar.setBackgroundTint(ContextCompat.getColor(binding.root.context, R.color.principal_color_dm))
                snackbar.setBackgroundTint(getResources().getColor(R.color.black))
                snackbar.show()
            }

        }




        binding.btnTwitter.setOnClickListener {

            locationContract.launch(Manifest.permission.ACCESS_FINE_LOCATION)

            /*val intent = Intent(
                Intent.ACTION_WEB_SEARCH,
            )
            intent.setClassName(
                "com.google.android.googlequicksearchbox",
                "com.google.android.googlequicksearchbox.SearchActivity"
            )
            intent.putExtra(SearchManager.QUERY,"Steam")
            startActivity(intent)*/
        }

        val appResultLocal = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){
                resultActivity->



            val sn = Snackbar.make(binding.txtName,
                "",Snackbar.LENGTH_LONG)



            var message = when (resultActivity.resultCode) {
                RESULT_OK->{
                    sn.setBackgroundTint(resources.getColor(R.color.red))
                    resultActivity.data?.getStringExtra("result").orEmpty()
                    "resultado Exitoso"



                }
                RESULT_CANCELED->{ "resultado fallido"
                    sn.setBackgroundTint(resources.getColor(R.color.blue))
                    resultActivity.data?.getStringExtra("result").orEmpty()

                }
                else->{
                    "resultado dudoso"

                }

            }
            sn.setText(message)
            sn.show()


        }
        binding.btnFacebook.setOnClickListener{
            val resIntent = Intent(this, ResultActivity::class.java)
            appResultLocal.launch(resIntent)

        }



        binding.btnFacebook.setOnClickListener(){
            val intentSpeach = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intentSpeach.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            intentSpeach.putExtra(

                RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault()
            )

            intentSpeach.putExtra(
                RecognizerIntent.EXTRA_PROMPT,
                "DI algo plox"
            )
            speechToText.launch(intentSpeach)
        }
    }


    override fun onStart() {
        super.onStart()
        initClass()

    }

    private suspend fun saveDataStore(stringData: String){
        dataStore.edit {prefs ->
            prefs[stringPreferencesKey("usuario")] = stringData
            prefs[stringPreferencesKey("session")] = UUID.randomUUID().toString()
            prefs[stringPreferencesKey("email")] = "dispositivosmoviles@uce.edu.ec"

        }
    }
}