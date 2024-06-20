package com.tuysuz.healthylifestyle;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class Cardio extends AppCompatActivity {

    TextView emails;
    private LineChart lineChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cardio);
         emails=findViewById(R.id.email);
        Intent intent=getIntent();
        if (intent!=null)
        {
            String receivedData = intent.getStringExtra("Email");

            emails.setText(receivedData);

        }
        lineChart = findViewById(R.id.lineChart);

        DataAdd();
    }

    private void DataAdd() {
        lineChart = findViewById(R.id.lineChart);

        // Günlük adım verilerini hazırla (örnek veriler)
        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0, 5000)); // 1. gün için adım sayısı
        entries.add(new Entry(1, 6000)); // 2. gün için adım sayısı
        entries.add(new Entry(2, 5500)); // 3. gün için adım sayısı
        entries.add(new Entry(3, 7000)); // 4. gün için adım sayısı
        entries.add(new Entry(4, 6500)); // 5. gün için adım sayısı
        entries.add(new Entry(5, 8000)); // 6. gün için adım sayısı
        entries.add(new Entry(6, 2000)); // 7. gün için adım sayısı

        // LineDataSet oluştur ve ayarla
        LineDataSet dataSet = new LineDataSet(entries, "Günlük Adımlar");

        // Çizgi rengi ve değer metni rengi ayarları
        dataSet.setColor(Color.BLUE);
        dataSet.setValueTextColor(Color.BLACK);

        // LineData oluştur ve LineChart'a ata
        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);

        // LineChart'ı özelleştirme
        Description description = new Description();
        description.setText("7 Günlük Adım Grafiği");
        lineChart.setDescription(description);

        Legend legend = lineChart.getLegend();
        legend.setForm(Legend.LegendForm.LINE);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        YAxis yAxisLeft = lineChart.getAxisLeft();
        YAxis yAxisRight = lineChart.getAxisRight();
        yAxisLeft.setAxisMinimum(0f);

        // LineChart'ı güncelle
        lineChart.invalidate();
    }
}