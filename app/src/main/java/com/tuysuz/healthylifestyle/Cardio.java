package com.tuysuz.healthylifestyle;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.appcompat.app.AppCompatActivity;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class Cardio extends AppCompatActivity {

    private LineChart lineChart;
    private List<Integer> stepCounts;
    String Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cardio);
        Button emails =findViewById(R.id.email);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("Email")) {
            String value = intent.getStringExtra("Email");
            if (value != null) {
                emails.setText(value);

            } else {
                emails.setText("test");

            }
        } else {
            // Intent null veya beklenen veri yoksa, uygun bir şekilde işlem yapın veya hata yönetimi sağlayın
        }



        lineChart = findViewById(R.id.lineChart);
        stepCounts = new ArrayList<>();
        stepCounts.add(2000); // Örneğin bir gün 2000 adım
        stepCounts.add(200);  // Örneğin diğer gün 200 adım
        stepCounts.add(4000);  // Örneğin diğer gün 200 adım

        lineChart = findViewById(R.id.lineChart);

        updateChart();


    }

    private void updateChart() {
        ArrayList<Entry> entries = new ArrayList<>();

        // Veri yapısından gelen adım sayılarını Entry listesine dönüştürüyoruz
        for (int i = 0; i < stepCounts.size(); i++) {
            entries.add(new Entry(i, stepCounts.get(i)));
        }

        LineDataSet lineDataSet = new LineDataSet(entries, "Daily Step Count");
        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);

        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);

        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);

        lineChart.getAxisRight().setEnabled(false);

        lineChart.invalidate(); // Grafik yenileniyor
    }
}