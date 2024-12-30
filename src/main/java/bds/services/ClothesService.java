package bds.services;

import bds.api.*;
import bds.data.ClothesRepository;

import java.util.List;


public class ClothesService {

    private ClothesRepository clothesRepository;

    public ClothesService(ClothesRepository clothesRepository) {
        this.clothesRepository = clothesRepository;
    }


    public List<ClothesBasicView> getClothesBasicView() {
        return clothesRepository.getClothesBasicView();
    }

    public void createClothes(ClothesCreateView clothesCreateView) {

        clothesRepository.createClothes(clothesCreateView);
        System.out.println("Clothes item was created successfully: " + clothesCreateView.getClothesName());
    }

    public void editClothes(ClothesEditView clothesEditView) {
        clothesRepository.editClothes(clothesEditView);
    }
    public void deleteClothes(ClothesBasicView selectedClothes) {
        clothesRepository.deleteClothes(selectedClothes.getIdClothes());
    }
}
