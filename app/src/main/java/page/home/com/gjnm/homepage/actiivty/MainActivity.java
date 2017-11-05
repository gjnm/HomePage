package page.home.com.gjnm.homepage.actiivty;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import page.home.com.gjnm.homepage.recommend.CardFactory;
import page.home.com.gjnm.homepage.recommend.CardListAdapter;
import page.home.com.gjnm.homepage.R;
import page.home.com.gjnm.homepage.recommend.carditem.OpBaseCardItem;
import page.home.com.gjnm.homepage.widget.UniteSlideLayout;

public class MainActivity extends AppCompatActivity {

    private UniteSlideLayout cardLayout;

    private List<OpBaseCardItem> cardList = new ArrayList<OpBaseCardItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        cardLayout = (UniteSlideLayout) findViewById(R.id.result_layout);
        View topView = inflater.inflate(R.layout.top_view, null);

        cardLayout.addTitleView(topView);
        cardList = CardFactory.getFunctionCardList();
        CardListAdapter adapter = new CardListAdapter(this, cardList);
        cardLayout.setCardAdapterWithAnim(adapter);
    }
}
