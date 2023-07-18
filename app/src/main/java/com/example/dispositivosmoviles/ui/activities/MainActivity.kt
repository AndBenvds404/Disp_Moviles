package com.example.dispositivosmoviles.ui.activities

import android.app.SearchManager
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
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult.*
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.databinding.ActivityMainBinding
import com.example.dispositivosmoviles.ui.validator.LoginValidator


import com.google.android.material.snackbar.Snackbar
import kotlin.math.log


val Context.dataStore: DataStore<Preferences>
        by preferencesDataStore(name = "settings")
class MainActivity : AppCompatActivity() {

    //Para enalzar y utilizar el Binding
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Los botones con binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

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
            val intent = Intent(
                Intent.ACTION_WEB_SEARCH,
            )
            intent.setClassName(
                "com.google.android.googlequicksearchbox",
                "com.google.android.googlequicksearchbox.SearchActivity"
            )
            intent.putExtra(SearchManager.QUERY,"Steam")
            startActivity(intent)
        }

        val appResultLocal = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){
            resultActivity->

            when (resultActivity.resultCode){
                RESULT_OK->{
                    Log.d("UCE", "resultado exitoso")
                    Snackbar.make(binding.txtName,"resultado exitoso",Snackbar.LENGTH_LONG)

                }
                RESULT_CANCELED->{
                    Log.d("UCE", "resultado fallido")
                    Snackbar.make(binding.txtName,"resultado fallido",Snackbar.LENGTH_LONG)
                }
                else->{Log.d("UCE", "resultado dudoso")
                    Snackbar.make(binding.txtName,"resultado dudoso",Snackbar.LENGTH_LONG)
                }

            }

        }
        binding.btnFacebook.setOnClickListener{
            val resIntent = Intent(this, ResultActivity::class.java)
            appResultLocal.launch(resIntent)

        }
    }

    /*fun abrirGoogle(){

        binding.btnTwitter.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW,Uri.parse("https://www.google.com/")
            )
            startActivity(intent)
        }

    }*/


    fun llamar(){

        binding.btnFacebook.setOnClickListener(){
            var uri = Uri.parse("tel:0963269273");
            var intent = Intent(Intent.ACTION_DIAL, uri);
            startActivity(intent)
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