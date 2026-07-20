import shlex
import subprocess
import tkinter as tk
from tkinter import messagebox


PACKAGE = "fr.smssansfrontiere"
ACTIVITY = f"{PACKAGE}/.MainActivity"


def telephone_connecte() -> bool:
    """Vérifie qu'ADB détecte au moins un téléphone autorisé."""
    try:
        resultat = subprocess.run(
            ["adb", "devices"],
            text=True,
            capture_output=True,
            check=False,
        )
    except FileNotFoundError:
        raise RuntimeError("ADB n'est pas installé sur cet ordinateur.")

    lignes = resultat.stdout.strip().splitlines()[1:]

    return any(
        ligne.strip().endswith("\tdevice")
        for ligne in lignes
    )


def envoyer_sms() -> None:
    numero = champ_numero.get().strip()
    message = champ_message.get("1.0", tk.END).strip()

    if not numero:
        messagebox.showwarning(
            "Numéro manquant",
            "Entre un numéro de téléphone."
        )
        return

    if not message:
        messagebox.showwarning(
            "Message manquant",
            "Écris le message à envoyer."
        )
        return

    try:
        if not telephone_connecte():
            raise RuntimeError(
                "Aucun téléphone autorisé n'est détecté par ADB."
            )

        commande_android = (
            f"am force-stop {shlex.quote(PACKAGE)}; "
            f"am start "
            f"-n {shlex.quote(ACTIVITY)} "
            f"--es numero {shlex.quote(numero)} "
            f"--es message {shlex.quote(message)}"
        )

        resultat = subprocess.run(
            ["adb", "shell", commande_android],
            text=True,
            capture_output=True,
            check=False,
        )

        if resultat.returncode != 0:
            erreur = resultat.stderr.strip() or resultat.stdout.strip()
            raise RuntimeError(erreur)

        etiquette_statut.config(
            text="Commande transmise à SMS Sans Frontière."
        )

        messagebox.showinfo(
            "Commande envoyée",
            "Le téléphone a reçu la demande d'envoi."
        )

    except RuntimeError as erreur:
        etiquette_statut.config(text="Échec de la transmission.")

        messagebox.showerror(
            "Erreur",
            str(erreur)
        )


fenetre = tk.Tk()
fenetre.title("Test SMS Sans Frontière")
fenetre.geometry("520x560")
fenetre.resizable(False, True)

titre = tk.Label(
    fenetre,
    text="SMS Sans Frontière",
    font=("Arial", 22, "bold")
)
titre.pack(pady=(25, 20))

cadre = tk.Frame(fenetre)
cadre.pack(fill="both", expand=True, padx=30)

tk.Label(
    cadre,
    text="Numéro de téléphone :",
    anchor="w"
).pack(fill="x")

champ_numero = tk.Entry(
    cadre,
    font=("Arial", 13)
)
champ_numero.pack(fill="x", pady=(5, 18))
champ_numero.focus_set()

tk.Label(
    cadre,
    text="Message :",
    anchor="w"
).pack(fill="x")

champ_message = tk.Text(
    cadre,
    height=8,
    font=("Arial", 12),
    wrap="word"
)
champ_message.pack(fill="x", pady=(5, 20))

bouton_envoyer = tk.Button(
    cadre,
    text="Envoyer le SMS",
    font=("Arial", 14, "bold"),
    command=envoyer_sms,
    height=2
)
bouton_envoyer.pack(fill="x")

etiquette_statut = tk.Label(
    cadre,
    text="Téléphone USB requis",
    font=("Arial", 10)
)
etiquette_statut.pack(pady=15)

fenetre.mainloop()

