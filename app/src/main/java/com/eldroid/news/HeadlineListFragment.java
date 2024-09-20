package com.eldroid.news;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

public class HeadlineListFragment extends Fragment {

    private List<Headline> headlines = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_headline_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Only add headlines if the list is empty
        if (headlines.isEmpty()) {
            // Example data with images
            headlines.add(new Headline("Comelec approves filing of misrepresentation raps vs Alice Guo",
                    "MANILA, Philippines — The Commission on Elections (Comelec) has approved the recommendation of its law department to file an election code complaint against suspended Bamban, Tarlac Mayor Alice Guo. \n" +
                            "\n" +
                            "Besides filing a material representation case, Comelec chairman George Garcia said on Tuesday that the poll body’s en banc also agreed order a preliminary investigation of the case. \n" +
                            "\n" +
                            "The complaint stemmed from a fact-finding investigation conducted earlier by the Comelec's law department regarding Guo’s mayoral candidacy, which may potentially violate Section 74 in relation to Section 262 of the Omnibus Election Code.\n" +
                            "\n" +
                            "Section 74 of the code states that a candidate must use their baptized name or, if not baptized, the name registered with the local civil registrar, unless they have legally changed their name through a court-approved process.\n" +
                            "\n" +
                            "Garcia also said that Guo would be given a chance to explain her side.\n" +
                            "\n" +
                            "In June, the National Bureau of Investigation confirmed that the fingerprints of Guo matched those of Chinese citizen Guo Hua Ping.\n" +
                            "\n" +
                            "When asked if the embattled mayor can still file for candidacy in the next elections, Garcia stated that the Comelec must accept all candidacy filings.\n" +
                            "\n" +
                            "Citing the Omnibus Election Code, the poll body’s chief said the Comelec must accept all the certificates of candidacies unless a court has ruled “with finality” that the aspiring candidate has been guilty of violating election laws. \n" +
                            "\n" +
                            "“Whether the candidate's name remains on the ballot depends on any filed cases against the candidate or if Comelec itself decides to remove the name due to ineligibility,” Garcia said in an ambush interview in Filipino. \n" +
                            "\n" +
                            "On July 5, the Office of the Solicitor General (OSG)  filed a petition before a Tarlac court to cancel Guo's certificate of live birth. Weeks later, the OSG filed a quo warranto petition against Guo to formally remove her from office. \n" +
                            "\n" +
                            "The OSG requested Guo's removal from public office, stating that she is \"unlawfully holding the position and illegally performing the duties and responsibilities of the mayor of Bamban, Tarlac.\"\n" +
                            "\n" +
                            "Still a no-show",
                    R.drawable.img));
            headlines.add(new Headline("Preacher wanted by FBI on sex crime charges evades Philippine police as followers resist in dayslong standoff",
                    "A violent standoff between Philippine police and followers of a fugitive preacher wanted by both the FBI and local law enforcement on sexual abuse and human trafficking charges entered a fourth day on Tuesday as nearly 2,000 officers surrounded a sprawling church compound.\n" +
                            "\n" +
                            "Pastor Apollo Carreon Quiboloy, a self-styled “appointed son of God” and founder of the Kingdom of Jesus Christ church, has been on the run for at least three years.\n" +
                            "\n" +
                            "A 2021 US indictment accuses the 74-year-old preacher and his alleged accomplices of running a sex trafficking ring that coerced girls and young women to have sex with him under threats of “eternal damnation.”\n" +
                            "\n" +
                            "Quiboloy, who has denied all the charges against him, is believed by Philippine police to be hiding inside a 30-hectare (75 acre) compound that includes a cathedral, a college, a bunker and a taxiway leading to Davao International Airport.\n" +
                            "\n" +
                            "Police have been attempting to arrest the preacher and five of his alleged accomplices in a raid that began on Saturday in the city in southern Philippines. But they have met fierce and at times violent resistance from his followers, who allegedly threw stones at officers and blocked a highway with burning tires, Davao police said on Facebook.\n" +
                            "\n" +
                            "A 51-year-old Quiboloy follower reportedly died of a heart attack on Saturday. Police said his death was not related to the operation.\n" +
                            "\n" +
                            "Police fired tear gas late Sunday as they tried to disperse the crowd. At least six officers have been injured and at least 18 people arrested during the dayslong standoff, police said.",
                    R.drawable.img_1));
            headlines.add(new Headline("UTOT NI ABBY",
                    "This is the news content for headline 3.",
                    R.drawable.img_1));
        }

        // Initialize the adapter and set it to the RecyclerView
        HeadlineAdapter adapter = new HeadlineAdapter(headlines, this::onHeadlineClick);
        recyclerView.setAdapter(adapter);
    }

    private void onHeadlineClick(Headline headline) {
        NewsContentFragment newsContentFragment = new NewsContentFragment();
        Bundle bundle = new Bundle();

        // Pass both the headline title, content, and image resource
        bundle.putString("headline_title", headline.getTitle());
        bundle.putString("news_content", headline.getContent());
        bundle.putInt("news_image_res_id", headline.getImageResId());
        newsContentFragment.setArguments(bundle);

        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (getResources().getConfiguration().orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE) {
            fragmentTransaction.replace(R.id.newsContentContainer, newsContentFragment, "NEWS_CONTENT_FRAGMENT");
        } else {
            fragmentTransaction.replace(R.id.headlineListContainer, newsContentFragment, "NEWS_CONTENT_FRAGMENT");
            fragmentTransaction.addToBackStack(null);
        }

        fragmentTransaction.commit();
    }
}
