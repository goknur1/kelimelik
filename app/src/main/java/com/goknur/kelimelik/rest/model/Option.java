package com.goknur.kelimelik.rest.model;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by goknur on 18.07.2017.
 */

@Parcel
public class Option {

    private boolean isCorrect;
    private String optionName;

    public Option(boolean isCorrect, String optionName) {
        this.isCorrect = isCorrect;
        this.optionName = optionName;
    }

    public Option() {
    }

  public boolean isCorrect()
  {
      return isCorrect;
  }
    public void setCorrect(boolean correct)  {
        isCorrect = correct;
    }

    public String getOptionName()  {
        return optionName;
    }

    public void setOptionName(String optionName)  {
        this.optionName = optionName;
    }

}
