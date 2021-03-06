package yeet.dungeonsomething.dungeoncharactercreator.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import yeet.dungeonsomething.dungeoncharactercreator.APIDataManager;
import yeet.dungeonsomething.dungeoncharactercreator.CharacterManager;
import yeet.dungeonsomething.dungeoncharactercreator.Model.Character;
import yeet.dungeonsomething.dungeoncharactercreator.Model.Note;
import yeet.dungeonsomething.dungeoncharactercreator.Model.Skill;
import yeet.dungeonsomething.dungeoncharactercreator.Model.StartingEquipment;
import yeet.dungeonsomething.dungeoncharactercreator.Model.Statistics;
import yeet.dungeonsomething.dungeoncharactercreator.R;
import yeet.dungeonsomething.dungeoncharactercreator.Views.Dialogs.HomeData;

public class CharacterHomeActivity extends AppCompatActivity {

    private Character myCharacter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_home);

//        Bundle b = getIntent().getExtras();
//        if(b != null){
//            myCharacter = loadMyCharacterData(b);
//        } else if(savedInstanceState != null){
//            myCharacter = loadMyCharacterData(savedInstanceState);
//        }
        myCharacter = CharacterManager.getInstance(getAssets()).getCharacter();

        if(myCharacter == null){
            Log.e("ERROR","Character data null on create");
            //create a BS new character so the app doesnt die
            myCharacter = new Character();
            myCharacter.setRace(APIDataManager.getInstance(getAssets()).getRaces()[0]);
            myCharacter.setMyclass(APIDataManager.getInstance(getAssets()).getClasses()[0]);
            myCharacter.setStats(new Statistics());
            myCharacter.getNotes().add(new Note("Title0", "SOME DESCRIPTION THAT HAPPENS TO BE REALLY REALLY REALLY SUPERDUPERPOOPERSCOOPER LONG"));
            myCharacter.getNotes().add(new Note("Title1", "SOME DESCRIPTION"));
            Skill[] skills = APIDataManager.getInstance(getAssets()).getSkills();
            Log.i("SKILL", "adding " + skills[0].getName());
            myCharacter.getSkillsProficentIn().add(skills[0]);

            CharacterManager.getInstance(getAssets()).newCharacter(myCharacter);
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        updateView();
    }

    private Character loadMyCharacterData(Bundle b){
        String name = b.getString("CHARACTER_NAME");
        Character c = null;
        if(name != null)
            c =  CharacterManager.getInstance(getAssets()).getCharacter(name);
        return c;
    }

    public void incHealth(View view){
        myCharacter.setHealthPoints(myCharacter.getHealthPoints()+1);
        ((TextView) findViewById(R.id.character_home_hp)).setText(String.valueOf(myCharacter.getHealthPoints()));
    }
    public void decHealth(View view){
        myCharacter.setHealthPoints(myCharacter.getHealthPoints()-1);
        ((TextView) findViewById(R.id.character_home_hp)).setText(String.valueOf(myCharacter.getHealthPoints()));
    }
    public void showEditDialog(View view){
        HomeData hd= new HomeData();
        hd.show(getFragmentManager(),"Edit");

    }

    public void updateView(){
        ((TextView) findViewById(R.id.character_home_name)).setText(myCharacter.getName());
        ((TextView) findViewById(R.id.character_home_race)).setText(myCharacter.getRace().getName());
        ((TextView) findViewById(R.id.character_home_class)).setText(myCharacter.getMyclass().getName());
        ((TextView) findViewById(R.id.character_home_level)).setText(String.valueOf(myCharacter.getLevel()));
        ((TextView) findViewById(R.id.character_home_armor_class)).setText(String.valueOf(myCharacter.getArmorClass()));
        ((TextView) findViewById(R.id.character_home_initative)).setText(String.valueOf(Statistics.getModifier(myCharacter.getStats().getDEX())));
        ((TextView) findViewById(R.id.character_home_speed)).setText(myCharacter.getRace().getSpeed() + "ft");
        ((TextView) findViewById(R.id.character_home_hp)).setText(String.valueOf(myCharacter.getHealthPoints()));
        ((TextView) findViewById(R.id.hitDice)).setText(myCharacter.getHitDice().getDice_count()+"d"+myCharacter.getHitDice().getDice_value());
    }

    public void showAttacks(View view){
        Intent intent = new Intent(this, AttacksActivity.class);
        intent.putExtra("CHARACTER_NAME", myCharacter.getName());
        startActivity(intent);
    }
    public void showStats(View view){
        Intent intent = new Intent(this, StatsActivity.class);
        intent.putExtra("CHARACTER_NAME", myCharacter.getName());
        startActivity(intent);
    }

    public void showBG(View view){
        Intent intent = new Intent(this, CharacterBGActivity.class);
        intent.putExtra("CHARACTER_NAME", myCharacter.getName());
        startActivity(intent);
    }

    public void showInventory(View view){
        Intent intent = new Intent(this, CharacterInventoryActivity.class);
        intent.putExtra("CHARACTER_NAME", myCharacter.getName());
        startActivity(intent);
    }

    public void showNotes(View view){
        Intent intent = new Intent(this, NoteActivity.class);
        intent.putExtra("CHARACTER_NAME", myCharacter.getName());
        startActivity(intent);
    }
}