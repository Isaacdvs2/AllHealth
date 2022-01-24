
package com.example.healthapp
import android.app.DownloadManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException

class MainActivity : AppCompatActivity() {
    lateinit var worldCasesTV: TextView
    lateinit var worldRecoveredTV: TextView
    lateinit var worldDeathsTV: TextView
    lateinit var countryCasesTV: TextView
    lateinit var countryRecoveredTV: TextView
    lateinit var countryDeathsTV: TextView
    lateinit var stateRV: RecyclerView
    lateinit var stateRVAdapter: StateRVAdapter
    lateinit var stateList: List<StateModel>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        worldCasesTV = findViewById(R.id.idTVWorldCases)
        worldRecoveredTV = findViewById(R.id.idTVWorldRecovered)
        worldDeathsTV = findViewById(R.id.idTVWorldDeaths)
        countryCasesTV = findViewById(R.id.idTVSACases)
        countryDeathsTV = findViewById(R.id.idTVSADeaths)
        countryRecoveredTV = findViewById(R.id.idTVSARecovered)
        stateRV = findViewById(R.id.idRSA)
        stateList = ArrayList<StateModel>()
    }

    // create the methods for getting the state and country info

    private fun getStateInfo(){
        val url = "https://api.rootnet.in/covid19-in/stats/latest"
        val queue =  Volley.newRequestQueue(this@MainActivity)
        val request = JsonObjectRequest(Request.Method.GET, url,null,{ response ->
            try{
                val dataObj = response.getJSONObject("data")
                val summaryObj = dataObj.getJSONObject("summary")
                val cases:Int = summaryObj.getInt("total")
                val recovered:Int = summaryObj.getInt("discharged")
                val deaths:Int =  summaryObj.getInt("deaths")

                countryCasesTV.text = cases.toString()
                countryRecoveredTV.text = recovered.toString()
                countryDeathsTV.text = deaths.toString()

                val regionalArray = dataObj.getJSONArray("regional")
                for (i in 0 until regionalArray.length()){
                    val regionalObj = regionalArray.getJSONObject(i)
                    val stateName:String = regionalObj.getString("loc")
                    val cases:Int = regionalObj.getInt("totalConfirmed")
                    val deaths:Int = regionalObj.getInt("deaths")
                    val recovered:Int = regionalObj.getInt("discharged")

                    // pass the values in the modal class
                    val stateModel = StateModel(stateName, recovered, deaths, cases)
                    stateList = stateList + stateModel
                }

                stateRVAdapter = StateRVAdapter(stateList)
                stateRV.layoutManager = LinearLayoutManager(this)
                stateRV.adapter = stateRVAdapter

            }catch(e:JSONException) {
                e.printStackTrace()
            }

        },{ error ->

            {
                Toast.makeText(this,"Fail to get data", Toast.LENGTH_SHORT).show()
            }
        })
        queue.add(request)
    }

    private fun getWorldinfo(){
        val url = "https://disease.sh/v3/covid-19/all"
        val queue = Volley.newRequestQueue(this@MainActivity)
        val request = JsonObjectRequest(Request.Method.GET,url,null,{response->
            try {
                val worldCases: Int = response.getInt("cases")
                val worldRecovered: Int = response.getInt("recovered")
                val worldDeaths: Int = response.getInt("deaths")

                worldCasesTV.text = worldCases.toString()
                worldRecoveredTV.text = worldCases.toString()
                worldDeathsTV.text = worldDeaths.toString()
            }
            catch (e: JSONException){
                e.printStackTrace()
            }
        },{
            error->
            Toast.makeText(this,"Fail to get date", Toast.LENGTH_SHORT).show()

        })
        queue.add(request)

    }
}