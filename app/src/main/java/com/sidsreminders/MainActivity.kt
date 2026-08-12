package com.sidsreminders
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.util.*

data class Event(val id:Long=System.nanoTime(), val type:String, val name:String, val day:Int, val month:Int, val year:Int?, val r7:Boolean, val r3:Boolean)

class MainActivity:ComponentActivity(){
 override fun onCreate(b:Bundle?){super.onCreate(b)
  if(android.os.Build.VERSION.SDK_INT>=33) requestPermissions(arrayOf("android.permission.POST_NOTIFICATIONS"),1)
  setContent{App()}
 }
}
@Composable fun App(){
 val events=remember{mutableStateListOf<Event>()}; var add by remember{mutableStateOf(false)}; var q by remember{mutableStateOf("")}
 MaterialTheme{
  Scaffold(topBar={TopAppBar(title={Text("Sid's Reminders")})},floatingActionButton={FloatingActionButton({add=true}){Text("+")}}){p->
   Column(Modifier.padding(p).padding(16.dp)){
    OutlinedTextField(q,{q=it},label={Text("Search")},modifier=Modifier.fillMaxWidth(),singleLine=true)
    Spacer(Modifier.height(10.dp))
    Text("${events.size} reminders",style=MaterialTheme.typography.titleMedium)
    Spacer(Modifier.height(10.dp))
    LazyColumn(verticalArrangement=Arrangement.spacedBy(8.dp)){
     items(events.filter{it.name.contains(q,true)}.sortedBy{it.month*100+it.day}){e->
      ElevatedCard(Modifier.fillMaxWidth()){Row(Modifier.padding(14.dp)){
       Column(Modifier.weight(1f)){Text(if(e.type=="Birthday")"🎂 ${e.name}" else "💍 ${e.name}",style=MaterialTheme.typography.titleMedium)
        Text("%02d/%02d".format(e.day,e.month)); Text("Same-day reminder • 9:00 AM")}
       TextButton({events.remove(e)}){Text("Delete")}
      }}
     }
    }
   }
  }
  if(add) AddDialog({add=false}){events.add(it);add=false}
 }
}
@Composable fun AddDialog(close:()->Unit,save:(Event)->Unit){
 var type by remember{mutableStateOf("Birthday")};var name by remember{mutableStateOf("")};var date by remember{mutableStateOf("")};var year by remember{mutableStateOf("")};var r7 by remember{mutableStateOf(false)};var r3 by remember{mutableStateOf(false)}
 AlertDialog(onDismissRequest=close,title={Text("Add Event")},text={Column(verticalArrangement=Arrangement.spacedBy(7.dp)){
  Row{FilterChip(type=="Birthday",{type="Birthday"},label={Text("Birthday")});Spacer(Modifier.width(8.dp));FilterChip(type=="Anniversary",{type="Anniversary"},label={Text("Anniversary")})}
  OutlinedTextField(name,{name=it},label={Text("Person / Couple")},singleLine=true)
  OutlinedTextField(date,{date=it},label={Text("Date DD/MM")},singleLine=true)
  OutlinedTextField(year,{year=it},label={Text("Year (optional)")},singleLine=true)
  Row{Checkbox(true,null);Text("Same day • mandatory • 9:00 AM")}
  Row{Checkbox(r7,{r7=it});Text("7 days before • 9:00 AM")}
  Row{Checkbox(r3,{r3=it});Text("3 days before • 9:00 AM")}
 }},confirmButton={TextButton({val x=date.split("/");if(name.isNotBlank()&&x.size==2)save(Event(type=type,name=name,day=x[0].toIntOrNull()?:1,month=x[1].toIntOrNull()?:1,year=year.toIntOrNull(),r7=r7,r3=r3))}){Text("Save")}},dismissButton={TextButton(close){Text("Cancel")}})
}
