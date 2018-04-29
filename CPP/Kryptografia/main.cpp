#include <iostream>
#include <windows.h>
#include <conio.h>

using namespace std;
char slowo1[50];
char slowo2[50];
char tab_kod[50];
char tab_dekod[50];
int a;
int b = 9;

void start ()
{
    cout<< endl;
    cout<<"**********************************************************************"<<endl;
    cout<<"*                        Projekt kryptograficzny                     *"<<endl;
    cout<<"*                                                                    *"<<endl;
    cout<<"*                                                                    *"<<endl;
    cout<<"*                                                                    *"<<endl;
    cout<<"*                                                    Pawel Chmiel    *"<<endl;
    cout<<"*                                                          227103    *"<<endl;
    cout<<"*                                                                    *"<<endl;
    cout<<"**********************************************************************"<<endl;
}
void menu()
{
    cout<<" --------------------------------------------------------------------"<<endl;
    cout<<"|                                                                    |"<<endl;
    cout<<"|    1. Zakoduj slowo                                                |"<<endl;
    cout<<"|    2. Dekoduj slowo                                                |"<<endl;
    cout<<"|    3. Zmiana wartosci kodowania                                    |"<<endl;
    cout<<"|    4. Wyjscie                                                      |"<<endl;
    cout<<"|                                                                    |"<<endl;
    cout<<" --------------------------------------------------------------------"<<endl;
    cout<<"         Wybor: ";
    while(!(cin>>a))
    {
        cin.clear();
        cin.sync();
        cout << "\n         Podaj cyfre 1-4"<<endl;
        cout << "         Wybor: ";
    }
    cout<<" ____________________________________________________________________"<<endl;
}

void kodowanie(){
    for(int i=0; i<50; i++) slowo1[i]=0;
    for(int i=0; i<50; i++) tab_kod[i]=0;
    cout<<endl;
    cout<<"         Podaj slowo, ktore chcesz zaszyfrowac: ";
    cin>>slowo1;

    for(int i=0; i<50; i++)
        if (slowo1[i]!=0)
            tab_kod[i]=slowo1[i]+b;
    cout<<"         Slowo zaszyfrowane: ";

    for(int i=0; i<50; i++)
        cout << tab_kod[i];
    cout<<endl;
}

void dekodowanie(){
    for(int i=0; i<50; i++) slowo2[i]=0;
    for(int i=0; i<50; i++) tab_dekod[i]=0;
    cout<<"         Wpisz zaszyfrowane slowo: ";
    cin>>slowo2;

    for(int i=0; i<50; i++)
        if(slowo2[i]!=0) tab_dekod[i]=slowo2[i]-b;
    cout<<"         Deszyfrowane slowo to: ";

    for(int i=0; i<50; i++)
        cout<<tab_dekod[i];
    cout<<endl;
}

int main()
{
    do{
        system("CLS");
        start();
        menu();
        switch(a)
        {
        case 1:{
            kodowanie();
        }
        break;
        case 2:{
            dekodowanie();
        }
        break;
        case 3:{
            cout<<"         Podaj wartosc przesuniecia kodowania: ";
            cin>>b;
        }
        break;
        case 4:
            break;
        }
        cout<<"\n         Nacisnij dowolny klawisz... " <<endl;
        getch();
    }
    while (a!=4);
}
