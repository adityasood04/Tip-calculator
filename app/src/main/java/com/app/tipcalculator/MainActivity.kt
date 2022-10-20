package com.app.tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.tipcalculator.ui.theme.TipCalculatorTheme
import kotlin.math.round

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipCalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    tipCalculatorScreen()
                }
            }
        }
    }
}

@Composable
fun tipCalculatorScreen() {

    Column {
        Text(text = "Tip Calculator", modifier= Modifier
            .padding(20.dp)
            .align(Alignment.CenterHorizontally),
        fontSize = 28.sp)
        Spacer(modifier = Modifier.height(10.dp))
        textFieldAndOutput()
    }
}

@Composable
fun textFieldAndOutput(){
    var amountInput = remember {
        mutableStateOf("")
    }
    var tipPercent = remember {
        mutableStateOf("")
    }
    var result by remember {
        mutableStateOf("")
    }
     var roundUp by remember {
         mutableStateOf(false)
     }
    val focusManager = LocalFocusManager.current
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TextField(
            value = amountInput.value,
            onValueChange = { newVal->
                amountInput.value = newVal
            },
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(), label = {
                Text(text = "Enter the bill amount")
            }, singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType =KeyboardType.Number,
            imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            })

        )
        TextField(
            value = tipPercent.value,
            onValueChange = { newVal->
                tipPercent.value = newVal
            },
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(), label = {
                Text(text = "Enter the tip percentage")
            }, singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType =KeyboardType.Number),
            keyboardActions = KeyboardActions(onDone = {focusManager.clearFocus()})
        )

        Button(onClick = {
            if (!roundUp) {
                var mResult =
                    (amountInput.value).toDouble() * Integer.parseInt(tipPercent.value) / 100
                    result= "Tip amount = Rs. ${mResult.toString()}"

            }
            else{
                var mResult =
                    (amountInput.value).toDouble() * Integer.parseInt(tipPercent.value) / 100
                mResult = kotlin.math.round(mResult)
                result= "Tip amount = Rs. ${mResult.toString()}"
            }
        }
            ) {
            Text(text = "Calculate")
        }

        Text(text = result, fontSize = 28.sp)
    }

}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TipCalculatorTheme {
        tipCalculatorScreen()
    }
}